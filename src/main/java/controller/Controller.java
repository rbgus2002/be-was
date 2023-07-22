package controller;

import annotation.RequestMapping;
import converter.ModelConverter;
import db.Database;
import db.Session;
import http.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpUtils;

import java.util.Map;

public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

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
    public HttpResponse login(Map<String, String> parameters, Session session) {
        String userId = parameters.get("userId");
        String password = parameters.get("password");
        try {
            User user = getValidateUser(userId, password);
            session.setAttribute("user", user);
            return HttpResponse.redirect("/index.html");
        } catch (IllegalArgumentException e) {
            logger.error("로그인에 실패했습니다.");
            return HttpResponse.redirect("/user/login_failed.html");
        }
    }

    private User getValidateUser(String userId, String password) {
        User user = Database.findUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("아이디 혹은 비밀번호가 틀렸습니다."));
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("아이디 혹은 비밀번호가 틀렸습니다.");
        }
        return user;
    }
}
