import resources.TestResource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResourcesServlet extends HttpServlet {
    private final ResourceServer resourceServer;

    ResourcesServlet (ResourceServer resourceServer) {
        this.resourceServer = resourceServer;
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String path = request.getParameter("path");
        TestResource resource = (TestResource) ReadXMLFileSAX.readXML(path);
        resourceServer.setTestResource(resource);

    }

}
