package controller;

import annotation.GetMapping;
import annotation.PostMapping;
import db.Database;
import db.SessionManager;
import http.HttpResponse;
import model.User;
import model.factory.UserFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

/**
 * "redirect:" 를 반환하는 경우 index.html로 이동한다.
 */
public class Controller {
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    @PostMapping(value = "/user/create")
    public HttpResponse createUser(Map<String, String> body) {
        User user = UserFactory.createUserFrom(body);
        Database.addUser(user);
        logger.debug("user 생성 : {}", Database.findUserById(body.get("userId")));

        return HttpResponse.redirect();
    }

    @PostMapping(value = "/user/login")
    public HttpResponse loginUser(Map<String, String> body) {
        Optional<User> user = Database.findUserById(body.get("userId"));
        if (user.isEmpty() || !user.get().checkPassword(body.get("password"))) {
            logger.error("아이디 혹은 비밀번호가 일치하지 않습니다");
            return HttpResponse.ok("/user/login_failed.html");
        }

        String sid = SessionManager.createUserSession(user.get());
        HttpResponse response = HttpResponse.redirect();
        response.addCookie("sid", sid);
        logger.debug("SESSION COOKIE 생성 :: {}", response);
        return response;
    }
}
