import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.management.*;
import javax.servlet.Servlet;
import java.lang.management.ManagementFactory;

public class Main {
    public static void main(String[] args) throws Exception {
        ResourceServer resourceServer = new ResourceServer();

        ResourceServerControllerMBean resourceServerControllerMBean = new ResourceServerController(resourceServer);
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("Admin:type=ResourceServerController");
        mbs.registerMBean(resourceServerControllerMBean, name);

        ResourcesServlet resourcesServlet = new ResourcesServlet(resourceServer);


        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder((Servlet) resourcesServlet), "/resources");

        Server server = new Server(8080);
        server.setHandler(contextHandler);

        server.start();
        java.util.logging.Logger.getGlobal().info("Server started");
        server.join();

    }
}
