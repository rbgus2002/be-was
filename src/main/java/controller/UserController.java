package controller;

import annotation.RequestMapping;
import db.Database;
import http.HttpMethod;
import http.HttpRequest;
import http.HttpResponse;
import http.MIME;
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
        Database.addUser(user);

        return "/index.html";
    }

    @RequestMapping(path = "/user/create", method = HttpMethod.POST)
    public String createUserByPOST(HttpRequest request, HttpResponse response) throws IOException {
        Map<String, String> params = Parser.parseParamsFromBody(request.getBody());

        User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
        Database.addUser(user);

        return "redirect:/index.html";
    }

    @RequestMapping(path = "/user/login", method = HttpMethod.POST)
    public String login(HttpRequest request, HttpResponse response) throws IOException {
        Map<String, String> params = Parser.parseParamsFromBody(request.getBody());
        String userId = params.get("userId");
        String password = params.get("password");

        // login 실패시 /user/index_failed.html 로 이동
        // TODO: 회원 인증 로직 메서드로 추출
        User user = Database.findUserById(userId);
        if (user == null || !user.getPassword().equals(password)) {
            return "redirect:/user/login_failed.html";
        }

        // login 성공시 /index.html 로 이동
        // 로그인이 성공할 경우 HTTP 헤더의 쿠키 값을 SID=세션 ID 로 응답한다.

        return "redirect:/index.html";
    }
}
