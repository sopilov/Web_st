import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends HttpServlet {
    private DBService dbService;

    public  SignInServlet (DBService dbService) {
        this.dbService = dbService;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        UsersDataSet usersDataSet = null;

        try {
            usersDataSet = dbService.getUser(login);
        } catch (DBException e) {
            e.printStackTrace();
        }

        if (usersDataSet != null && usersDataSet.getName().equalsIgnoreCase(login) && usersDataSet.getPassword().equals(password)) {
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("Authorized: " + login);
        } else {
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("Unauthorized");
        }
    }
}
