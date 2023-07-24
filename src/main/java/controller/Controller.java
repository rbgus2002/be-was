package controller;

import annotation.RequestMapping;
import converter.ModelConverter;
import db.Database;
import db.Session;
import http.HttpResponse;
import http.Mime;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpUtils;

import java.util.Map;

public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    @RequestMapping(path = "/index.html", method = HttpUtils.Method.GET)
    public HttpResponse index(Session session) {
        HttpResponse httpResponse = HttpResponse.ok("/index.html", Mime.HTML);
        String userId = (String) session.getAttribute("userId");
        httpResponse.setViewParameters("view", "index");
        if (userId == null) {
            logger.debug("로그인되지 않은 사용자입니다.");
            return httpResponse;
        }
        User user = Database.findUserById(userId).orElseThrow(() -> {
            session.removeAttribute(userId);
            return new IllegalArgumentException("존재하지 않는 사용자입니다.");
        });
        httpResponse.setViewParameters("name", user.getName());
        logger.debug("로그인된 사용자입니다.");
        return httpResponse;
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
    public HttpResponse login(Map<String, String> parameters, Session session) {
        String userId = parameters.get("userId");
        String password = parameters.get("password");
        try {
            validateUser(userId, password);
            session.setAttribute("userId", userId);
            return HttpResponse.redirect("/index.html");
        } catch (IllegalArgumentException e) {
            logger.error("로그인에 실패했습니다.");
            return HttpResponse.redirect("/user/login_failed.html");
        }
    }

    private void validateUser(String userId, String password) {
        User user = Database.findUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("아이디 혹은 비밀번호가 틀렸습니다."));
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("아이디 혹은 비밀번호가 틀렸습니다.");
        }
    }
}
