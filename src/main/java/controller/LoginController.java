package controller;

import db.Database;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.util.Map;

public class LoginController implements Controller{

    public static final String USERID_KEY = "userId";
    public static final String PASSWORD_KEY = "password";
    public static final String REDIRECT_LOGIN_FAILED = "redirect:/user/login_failed.html";
    public static final String REDIRECT_HOME = "redirect:/index.html";
    @Override
    public String execute(HttpRequest request, HttpResponse response) {

        if(canLogin(request, response)) {
            return REDIRECT_HOME;
        }
        return REDIRECT_LOGIN_FAILED;
    }

    private boolean canLogin(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> data = httpRequest.getFormDataMap();
        User user = Database.findUserById(data.get(USERID_KEY));
        if(user == null) {
            return false;
        }
        if(!user.getPassword().equals(data.get(PASSWORD_KEY))) {
            return false;
        }
        httpResponse.setCookie(user.getSessionId());
        return true;
    }
}
