package controller;

import db.Database;
import model.Session;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.annotation.Controller;
import support.annotation.RequestMapping;
import support.annotation.RequestParam;
import support.exception.FoundException;
import support.web.HttpMethod;
import support.web.ModelAndView;
import utils.LoginUtils;
import webserver.Cookie;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Controller(value = "/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(method = HttpMethod.POST, value = "/login")
    public void login(@RequestParam("userId") String userId,
                      @RequestParam("password") String password,
                      HttpResponse response) throws FoundException {
        logger.debug("유저 로그인 요청");

        User user = Database.findUserById(userId);
        if (user == null || !user.getPassword().equals(password)) {
            logger.debug("유저 없거나 비밀번호 불일치");
            throw new FoundException("/user/login_failed.html");
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

        throw new FoundException("/index");
    }

    @RequestMapping(method = HttpMethod.POST, value = "/create")
    public void create(@RequestParam("userId") String userId,
                       @RequestParam("password") String password,
                       @RequestParam("name") String name,
                       @RequestParam("email") String email) throws FoundException {

        logger.debug("유저 생성 요청");
        User user = new User(userId, password, name, email);
        Database.addUser(user);

        throw new FoundException("/index");
    }

    @RequestMapping(method = HttpMethod.GET, value = "/list")
    public ModelAndView userList(HttpRequest request) throws FoundException {
        logger.debug("리스트 요청");

        Session loginSession = LoginUtils.getLoginSession(request);
        if (loginSession != null) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("/user/list");
            return modelAndView;
        }

        throw new FoundException("/user/login.html");
    }

    @RequestMapping(method = HttpMethod.GET, value = "/logout")
    public void logout(HttpRequest request, HttpResponse response) throws FoundException {
        logger.debug("로그 아웃 요청");

        Session loginSession = LoginUtils.getLoginSession(request);
        if (loginSession != null) {
            Database.deleteById(loginSession.getSessionId());
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z")
                    .withZone(ZoneId.of("GMT"));
            Cookie.CookieBuilder cookieBuilder = new Cookie.CookieBuilder();
            Cookie cookie = cookieBuilder.key("sid")
                    .value("")
                    .path("/")
                    .expires(formatter.format(now))
                    .build();
            response.appendHeader("Set-Cookie", cookie.buildCookie());
        }

        throw new FoundException("/");
    }

}
