package container;

import db.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

import static util.PathList.HOME_PATH;

public class ListController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(ListController.class);
    @Override
    @RequestMapping
    public String process(HTTPServletRequest request, HTTPServletResponse response) {
        if (isLogInStatus(request)) {
            return HOME_PATH.getPath();
        }
        return "/user/list.html";
    }

    private boolean isLogInStatus(HTTPServletRequest request) {
        logger.debug("SessionManager.getSession(request) = {}", SessionManager.getSession(request));
         return SessionManager.getSession(request) == null;
    }
}
