package container;

import annotation.RequestMapping;
import db.SessionManager;
import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

@RequestMapping(path = "/user/profile")
public class ProfileController implements Controller{
    @Override
    public String process(HTTPServletRequest request, HTTPServletResponse response) {
        if(isLogInStatus(request)) {
            return "/user/profile.html";
        }
        return "/user/login.html";
    }

    private boolean isLogInStatus(HTTPServletRequest request) {
        return SessionManager.getSession(request) != null;
    }
}
