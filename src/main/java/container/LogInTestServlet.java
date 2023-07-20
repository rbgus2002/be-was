package container;

import db.Database;
import model.User;
import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

import static util.PathList.FAILED_PATH;
import static util.PathList.HOME_PATH;

public class LogInTestServlet implements Servlet{
    @Override
    public void service(HTTPServletRequest request, HTTPServletResponse response) throws IOException {
        Map<String, String> query = request.getQuery();
        String userId = query.get("userId");
        try {
            Database.findUserById(userId);
            response.setHeader("Location", HOME_PATH.getPath());
        } catch (Exception e) {
            response.setHeader("Location", FAILED_PATH.getPath());
        }

        String version = request.getVersion();
        response.setVersion(version);
        response.setStatusCode("302");
        response.setStatusMessage("Found");

        DataOutputStream writer = response.getWriter();
        writer.writeBytes(response.info());
        writer.flush();
    }
}
