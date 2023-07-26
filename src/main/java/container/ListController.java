package container;

import db.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

import static util.PathList.HOME_PATH;
@RequestMapping(path = "/user/list")
public class ListController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(ListController.class);
    @Override
    public String process(HTTPServletRequest request, HTTPServletResponse response) {
        if (isLogInStatus(request)) {
            return "/user/list.html";
        }
        return "/user/login.html";
    }

    private boolean isLogInStatus(HTTPServletRequest request) {
        logger.debug("SessionManager.getSession(request) = {}", SessionManager.getSession(request));
         return SessionManager.getSession(request) != null;
    }
}
