package container;

import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

import java.io.DataOutputStream;
import java.io.IOException;

import static util.PathList.HOME_PATH;

public class LogInSuccessServlet implements Servlet{
    @Override
    public void service(HTTPServletRequest request, HTTPServletResponse response) throws IOException {
        String version = request.getVersion();
        response.setVersion(version);
        response.setStatusCode("302");
        response.setStatusMessage("Found");
        response.setHeader("Location", HOME_PATH.getPath());

        DataOutputStream writer = response.getWriter();
        writer.writeBytes(response.info());
        writer.flush();
    }
}
