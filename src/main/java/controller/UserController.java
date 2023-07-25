package controller;

import annotation.RequestMapping;
import db.UserDatabase;
import http.HttpMethod;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Parser;

import java.io.IOException;
import java.util.Map;

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(path = "/user/create", method = HttpMethod.GET)
    public String createUserByGET(HttpRequest request, HttpResponse response) throws IOException {
        Map<String, String> params = request.getParams();

        User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
        UserDatabase.addUser(user);

        return "/index.html";
    }

    @RequestMapping(path = "/user/create", method = HttpMethod.POST)
    public String createUserByPOST(HttpRequest request, HttpResponse response) throws IOException {
        Map<String, String> params = Parser.parseParamsFromBody(request.getBody());

        // TODO: 예외처리 및 에러페이지로 이동
        if(UserDatabase.findUserById(params.get("userId")) != null) {
            return "redirect:/index.html";
        }

        User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
        UserDatabase.addUser(user);

        return "redirect:/index.html";
    }

    @RequestMapping(path = "/user/login", method = HttpMethod.POST)
    public String login(HttpRequest request, HttpResponse response) throws IOException {
        Map<String, String> params = Parser.parseParamsFromBody(request.getBody());
        String userId = params.get("userId");
        String password = params.get("password");

        User user = UserDatabase.findUserById(userId);

        if (validateUser(user, password)) {
            // login 실패시 /user/index_failed.html 로 이동
            return "redirect:/user/login_failed.html";
        }

        // 로그인이 성공할 경우 HTTP 헤더의 쿠키 값을 SID=세션 ID 로 응답한다.

        // login 성공시 /index.html 로 이동
        return "redirect:/index.html";
    }

    private boolean validateUser(User user, String password) {
        if(user == null || !user.getPassword().equals(password)) {
            return false;
        }
        return true;
    }
}
