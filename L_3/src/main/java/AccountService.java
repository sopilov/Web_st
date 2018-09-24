import java.util.HashMap;

public class AccountService {

    private DBService dbService = new DBService();

    public void addNewUser(UserProfile userProfile) {
        try {
            dbService.addUser(userProfile);
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    public UserProfile getUserByLogin(String login) {
        UserProfile userProfile = null;
        try {
            userProfile = dbService.getUser(login);
        } catch (DBException e) {
            e.printStackTrace();
        }
        return userProfile;
    }

}
