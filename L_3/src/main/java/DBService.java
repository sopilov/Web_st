import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBService {
    private final Connection connection;

    public DBService() {
        this.connection = getH2Connection();
    }

    public UserProfile getUser(String login) throws DBException {
        try {
            return (new UsersDAO(connection).get(login));
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void addUser(UserProfile userProfile) throws DBException {
        try {
            connection.setAutoCommit(false);
            UsersDAO dao = new UsersDAO(connection);
            dao.createTable();
            dao.insertUser(userProfile.getLogin(), userProfile.getPass());
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
            throw new DBException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {
            }
        }
    }

    public static Connection getH2Connection() {
        try {
            String url = "jdbc:h2:./h2db";
            String name = "test";
            String pass = "test";

            Connection connection = DriverManager.getConnection(url, name, pass);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //достать все записи при помощи Executor
    public void getAllFromExecutor () throws SQLException {
        System.out.println("Test method getAllFromExecutor:");
        Executor executor = new Executor(connection);
        List<UserProfile> userProfiles= executor.execQuery("select * from users", result -> {
            List<UserProfile> users = new ArrayList<>();
            while (result.next()) {
                users.add(new UserProfile(result.getString("login"), result.getString("password")));
                System.out.println("User № " + result.getString("ID") + " login:" + result.getString("login") + ", pass:" + result.getString("password")); //для проверки
            }
            return users;
        });
    }

    //достать все записи через Statement
    public void getAllFromStatement () throws SQLException {
        System.out.println("Test method getAllFromStatement:");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from users");
        while (resultSet.next()) {
            System.out.println("User № " + resultSet.getString("ID") + " login:" + resultSet.getString("login") + ", pass:" + resultSet.getString("password")); //для проверки
        }
        statement.close();
    }

    // использование PreparedStatement
    public void getUsePraparedStatement () throws  SQLException {
        System.out.println("getUsePraparedStatement:");
        String SQL = "select * from users where ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ResultSet resultSet = null;
        for (int i = 1; i <6; i++) {
            preparedStatement.setInt(1, i);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            System.out.println("User № " + resultSet.getString("ID") + " login:" + resultSet.getString("login") + ", pass:" + resultSet.getString("password"));
        }
        preparedStatement.close();
    }


}
