package controller;

import annotation.RequestMapping;
import db.SessionDatabase;
import db.UserDatabase;
import http.HttpMethod;
import http.HttpRequest;
import http.HttpResponse;
import http.HttpSession;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Parser;

import java.util.Map;
import java.util.UUID;

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final UserController instance = new UserController();

    private UserController() {
    }

    public static UserController getInstance() {
        return instance;
    }

    @RequestMapping(path = "/user/create", method = HttpMethod.POST)
    public String createUser(HttpRequest request, HttpResponse response) {
        Map<String, String> params = Parser.parseParamsFromBody(request.getBody());

        // TODO: 예외처리 및 에러페이지로 이동
        if (validateDuplicatedUser(params)) {
            User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
            UserDatabase.addUser(user);
        }

        return "redirect:/index.html";
    }

    private static boolean validateDuplicatedUser(Map<String, String> params) {
        return UserDatabase.findUserById(params.get("userId")) == null;
    }

    @RequestMapping(path = "/user/login", method = HttpMethod.POST)
    public String login(HttpRequest request, HttpResponse response) {
        Map<String, String> params = Parser.parseParamsFromBody(request.getBody());
        String userId = params.get("userId");
        String password = params.get("password");

        User user = UserDatabase.findUserById(userId);

        // login 성공시 /index.html 로 이동
        if (validateUser(user, password)) {
            // Set-Cookie
            String sessionId = UUID.randomUUID().toString();
            response.setCookie(sessionId);

            HttpSession session = new HttpSession(sessionId);
            SessionDatabase.addSession(sessionId, session);
            session.setAttributes("user", user);
            return "redirect:/index.html";
        }

        // login 실패시 /user/index_failed.html 로 이동
        return "redirect:/user/login_failed.html";
    }

    private boolean validateUser(User user, String password) {
        return !(user == null || !user.getPassword().equals(password));
    }
}
