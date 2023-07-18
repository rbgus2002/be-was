package controller;

import annotation.RequestMapping;
import converter.ModelConverter;
import db.Database;
import http.HttpResponse;
import model.User;
import util.HttpUtils;

import java.util.Map;

public class Controller {

    private Controller() {
    }

    private static class SingletonHelper {
        private static final Controller INSTANCE = new Controller();
    }

    public static Controller getInstance() {
        return SingletonHelper.INSTANCE;
    }

    @RequestMapping(path = "/user/create", method = HttpUtils.Method.POST)
    public HttpResponse creatUser(Map<String, String> parameters) {
        User newUser = ModelConverter.toUser(parameters);
        Database.findUserById(newUser.getUserId()).ifPresent(user -> {
            throw new IllegalArgumentException("중복된 userId입니다.");
        });
        Database.addUser(newUser);
        return HttpResponse.redirect("/index.html");
    }

    @RequestMapping(path = "/user/login", method = HttpUtils.Method.POST)
    public HttpResponse login(Map<String, String> parameters) {
        String userId = parameters.get("userId");
        String password = parameters.get("password");
        validateLogin(userId, password);
        return HttpResponse.redirect("/index.html");
    }

    private void validateLogin(String userId, String password) {
        User user = Database.findUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("아이디 혹은 비밀번호가 틀렸습니다."));
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("아이디 혹은 비밀번호가 틀렸습니다.");
        }
    }
}
