package container;

import annotation.RequestMapping;
import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

import java.util.Map;

import static util.PathList.HOME_PATH;
@RequestMapping(path = "/user/create")
public class LogInController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(LogInController.class);

    @Override
    public String process(HTTPServletRequest request, HTTPServletResponse response) {
        Map<String, String> query = request.getQuery();
        User user = new User(query.get("userId"), query.get("password"), query.get("name"), query.get("email"));
        logger.debug("user = {}", user);
        Database.addUser(user);

        return HOME_PATH.getPath();
    }
}
