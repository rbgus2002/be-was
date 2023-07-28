package container;

import annotation.RequestMapping;
import db.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.PathList;
import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

import static util.PathList.*;

@RequestMapping(path = "/user/logout")
public class LogOutController implements Controller{

    private static final Logger logger = LoggerFactory.getLogger(LogOutController.class);
    @Override
    public String process(HTTPServletRequest request, HTTPServletResponse response) {
        String sid = request.getHeader("Cookie").split("=")[1].trim();
        response.setHeader("Set-Cookie", "SID=" + sid + "; Path=/" + "; Max-Age=0;");
        SessionManager.invalidateSession(sid);
        return HOME_PATH.getPath();
    }

}
