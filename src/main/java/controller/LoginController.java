package controller;

import db.Database;
import model.User;
import webserver.http.request.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.util.Map;

public class LoginController implements Controller{

    public static final String USERID_KEY = "userId";
    public static final String PASSWORD_KEY = "password";
    public static final String REDIRECT_LOGIN_FAILED = "redirect:/user/login_failed.html";
    public static final String REDIRECT_HOME = "redirect:/index.html";
    @Override
    public HttpResponse execute(HttpRequest request, HttpResponse response) {

        response.setToUrl(REDIRECT_LOGIN_FAILED);
        if(canLogin(request, response)) {
            response.setToUrl(REDIRECT_HOME);
        }
        return response;
    }

    private boolean canLogin(HttpRequest httpRequest, HttpResponse httpResponse) {
        Map<String, String> data = httpRequest.getQueries();
        if(httpRequest.getMethod() == HttpMethod.POST) {
            data = httpRequest.getBodies();
        }

        User user = Database.findUserById(data.get(USERID_KEY));
        if(user == null) {
            return false;
        }
        if(!user.getPassword().equals(data.get(PASSWORD_KEY))) {
            return false;
        }
        httpResponse.setCookie(httpResponse.getCookie());
        return true;
    }
}
