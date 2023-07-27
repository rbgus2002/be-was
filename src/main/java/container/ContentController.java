package container;

import annotation.RequestMapping;
import db.SessionManager;
import org.checkerframework.checker.units.qual.C;
import util.PathList;
import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

import static util.PathList.*;

@RequestMapping(path = "/qna/show.html")
public class ContentController implements Controller {
    @Override
    public String process(HTTPServletRequest request, HTTPServletResponse response) {
        if (isLogInStatus(request)) {
            return "/qna/show.html";
        }
        return "/user/login.html";
    }

    private boolean isLogInStatus(HTTPServletRequest request) {
        return SessionManager.getSession(request) != null;
    }
}
