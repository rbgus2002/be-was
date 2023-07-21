package container;

import db.Database;
import db.SessionManager;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

import static util.PathList.FAILED_PATH;
import static util.PathList.HOME_PATH;

public class LogInTestServlet implements Servlet {

    private static final Logger logger = LoggerFactory.getLogger(LogInTestServlet.class);

    @Override
    public void service(HTTPServletRequest request, HTTPServletResponse response) throws IOException {
        Map<String, String> query = request.getQuery();
        String userId = query.get("userId");
        User findUser = Database.findUserById(userId);
        logger.debug("findUser = {}", findUser);
        if(findUser == null){
            logger.debug("failed");
            response.setHeader("Location", FAILED_PATH.getPath());
        }else {
            response.setHeader("Location", HOME_PATH.getPath());
            SessionManager.createSession(findUser, response);
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
