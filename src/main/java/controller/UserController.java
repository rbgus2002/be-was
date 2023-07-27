package controller;

import db.Database;
import model.Session;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.annotation.Controller;
import support.annotation.RequestMapping;
import support.annotation.RequestParam;
import support.web.HttpMethod;
import support.web.ModelAndView;
import support.web.ResponseEntity;
import utils.LoginUtils;
import webserver.Cookie;
import webserver.Header;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Controller(value = "/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(method = HttpMethod.POST, value = "/login")
    public ResponseEntity<?> login(@RequestParam("userId") String userId,
                                   @RequestParam("password") String password,
                                   HttpResponse response) {
        logger.debug("유저 로그인 요청");

        User user = Database.findUserById(userId);
        if (user == null || !user.getPassword().equals(password)) {
            logger.debug("유저 없거나 비밀번호 불일치");
            Header header = new Header();
            header.setLocation("/user/login_failed.html");
            return new ResponseEntity<>(HttpStatus.FOUND, header);
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


        Header header = new Header();
        header.setLocation("/");
        return new ResponseEntity<>(HttpStatus.FOUND, header);
    }

    @RequestMapping(method = HttpMethod.POST, value = "/create")
    public ResponseEntity<?> create(@RequestParam("userId") String userId,
                                    @RequestParam("password") String password,
                                    @RequestParam("name") String name,
                                    @RequestParam("email") String email) {

        logger.debug("유저 생성 요청");
        User user = new User(userId, password, name, email);
        Database.addUser(user);

        Header header = new Header();
        header.setLocation("/");
        return new ResponseEntity<>(HttpStatus.FOUND, header);
    }

    @RequestMapping(method = HttpMethod.GET, value = "/list")
    public ResponseEntity<ModelAndView> userList(HttpRequest request) {
        logger.debug("리스트 요청");

        Session loginSession = LoginUtils.getLoginSession(request);
        if (loginSession != null) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("/user/list");

            Header header = new Header();
            header.setLocation("/user/login.html");
            return new ResponseEntity<>(HttpStatus.FOUND, header, modelAndView);
        }

        Header header = new Header();
        header.setLocation("/user/login.html");
        return new ResponseEntity<>(HttpStatus.FOUND, header);
    }

    @RequestMapping(method = HttpMethod.GET, value = "/logout")
    public ResponseEntity<?> logout(HttpRequest request, HttpResponse response) {
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

        Header header = new Header();
        header.setLocation("/");
        return new ResponseEntity<>(HttpStatus.FOUND, header);
    }

}
