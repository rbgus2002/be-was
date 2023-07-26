package controller;

import Service.UserService;
import annotation.Controller;
import annotation.RequestMapping;
import session.SessionStorage;
import webserver.http.request.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.util.Map;

@Controller
public class LoginController{

    public static final String USERID_KEY = "userId";
    public static final String PASSWORD_KEY = "password";
    public static final String REDIRECT_LOGIN_FAILED = "redirect:/user/login_failed.html";
    public static final String REDIRECT_HOME = "redirect:/index.html";

    @RequestMapping(path = "/user/login")
    public void login(HttpRequest request, HttpResponse response) {
        UserService userService = UserService.of();
        response.setToUrl(REDIRECT_LOGIN_FAILED);

        Map<String, String> data = request.getQueries();
        if(request.getMethod() == HttpMethod.POST) {
            data = request.getBodies();
        }
        String userId = data.get(USERID_KEY);
        String userPw = data.get(PASSWORD_KEY);
        if(userService.canLogin(userId, userPw)) {
            response.setToUrl(REDIRECT_HOME);
            String sessionId = SessionStorage.setSession(userId);
            response.setCookie("sid=" + sessionId);
        }
    }


}
