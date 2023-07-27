package container;

import annotation.RequestMapping;
import db.SessionManager;
import util.PathList;
import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

import static util.PathList.*;

@RequestMapping(path = "/qna/form")
public class WriteController implements Controller{
    @Override
    public String process(HTTPServletRequest request, HTTPServletResponse response) {
        if (request.getMethod().equals("POST")) {
            return HOME_PATH.getPath();
        }

        if (isLogInStatus(request)) {
           return "/qna/form.html";
        }
        return "/user/login.html";
    }

    private boolean isLogInStatus(HTTPServletRequest request) {
        return SessionManager.getSession(request) != null;
    }
}
