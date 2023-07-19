package controller;

import db.Database;
import model.User;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.util.Map;

public class LoginController implements Controller{

    public static final String USERID_KEY = "userId";
    public static final String PASSWORD_KEY = "password";
    @Override
    public String execute(HttpRequest request, HttpResponse response) {
        if(canLogin(request)) {

            return "redirect:/index.html";
        }
        return "redirect:/user/login_failed.html";
    }

    private boolean canLogin(HttpRequest httpRequest) {
        Map<String, String> data = httpRequest.getFormDataMap();
        User user = Database.findUserById(data.get(USERID_KEY));
        if(user == null) {
            return false;
        }
        return user.getPassword().equals(data.get(PASSWORD_KEY));
    }
}
