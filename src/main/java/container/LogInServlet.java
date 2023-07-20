package container;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

import static util.PathList.HOME_PATH;

public class LogInServlet implements Servlet {

    private static final Logger logger = LoggerFactory.getLogger(LogInServlet.class);

    @Override
    public void service(HTTPServletRequest request, HTTPServletResponse response) throws IOException {
        Map<String, String> query = request.getQuery();
        User user = new User(query.get("userId"), query.get("password"), query.get("name"), query.get("email"));
        logger.debug("user = {}", user);
        Database.addUser(user);
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
