package controller;

import db.Database;
import model.Session;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.HttpMethod;
import support.annotation.Controller;
import support.annotation.RequestMapping;
import support.annotation.RequestParam;
import webserver.Cookie;
import webserver.response.HttpResponse;

@Controller(value = "/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(method = HttpMethod.POST, value = "/login")
    public String login(@RequestParam("userId") String userId,
                      @RequestParam("password") String password,
                      HttpResponse response) {
        logger.debug("유저 로그인 요청");

        User user = Database.findUserById(userId);
        if (user == null || !user.getPassword().equals(password)) {
            logger.debug("유저 없거나 비밀번호 불일치");
            return "/user/login_failed.html";
        }
        logger.debug("유저 로그인 성공~");

        // 세션 & 쿠키 세팅
        Session session = new Session();
        Database.addSession(session);
        session.setUser(user);

        Cookie.CookieBuilder cookieBuilder = new Cookie.CookieBuilder();
        cookieBuilder.key("sid");
        cookieBuilder.value(session.getSessionId());
        cookieBuilder.path("/");
        Cookie cookie = cookieBuilder.build();
        response.appendHeader("Set-Cookie", cookie.buildCookie());

        return "/index.html";
    }

    @RequestMapping(method = HttpMethod.POST, value = "/create")
    public String create(@RequestParam("userId") String userId,
                       @RequestParam("password") String password,
                       @RequestParam("name") String name,
                       @RequestParam("email") String email) {

        logger.debug("유저 생성 요청");
        User user = new User(userId, password, name, email);
        Database.addUser(user);

        return "/index.html";
    }

}
