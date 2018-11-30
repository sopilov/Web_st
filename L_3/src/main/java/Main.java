import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception {
        DBService dbService = new DBService();

        SignInServlet signInServlet = new SignInServlet(dbService);
        SignUpServlet signUpServlet = new SignUpServlet(dbService);

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder(signInServlet), "/signin");
        contextHandler.addServlet(new ServletHolder(signUpServlet), "/signup");

        Server server = new Server(8080);
        server.setHandler(contextHandler);

        dbService.getAllFromExecutor();
        dbService.getAllFromStatement();
        dbService.getUsePraparedStatement();

        server.start();
        java.util.logging.Logger.getGlobal().info("Server started");
        server.join();
    }
}
