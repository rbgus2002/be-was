package container;

import db.Database;
import db.SessionManager;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

import java.util.Map;

import static util.PathList.FAILED_PATH;
import static util.PathList.HOME_PATH;

public class LogInTestController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(LogInTestController.class);

    @Override
    public String process(HTTPServletRequest request, HTTPServletResponse response) {
        Map<String, String> query = request.getQuery();
        String userId = query.get("userId");
        User findUser = Database.findUserById(userId);
        logger.debug("findUser = {}", findUser);
        if (findUser == null) {
            return FAILED_PATH.getPath();
        }
        SessionManager.createSession(findUser, response);
        return HOME_PATH.getPath();
    }
}
