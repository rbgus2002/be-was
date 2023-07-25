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
import webserver.Cookie;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.util.Arrays;
import java.util.Optional;

@Controller(value = "/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(method = HttpMethod.POST, value = "/login")
    public String login(@RequestParam("userId") String userId,
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

        throw new FoundException("/index.html");
    }

    @RequestMapping(method = HttpMethod.POST, value = "/create")
    public String create(@RequestParam("userId") String userId,
                         @RequestParam("password") String password,
                         @RequestParam("name") String name,
                         @RequestParam("email") String email) throws FoundException {

        logger.debug("유저 생성 요청");
        User user = new User(userId, password, name, email);
        Database.addUser(user);

        throw new FoundException("/index.html");
    }

    @RequestMapping(method = HttpMethod.GET, value = "/list")
    public String userList(HttpRequest request) throws FoundException {
        logger.debug("리스트 요청");

        String cookieHeader = request.getHeaderValue("Cookie");
        if (cookieHeader != null) {
            String[] headerValue = cookieHeader.split(" ");
            Optional<String> sid = Arrays.stream(headerValue).filter(s -> s.startsWith("sid")).findAny();
            if (sid.isPresent()) {
                String rsid = sid.get();
                Session session = Database.findSessionById(rsid.substring(4));
                if (session != null) {
                    return "/user/list";
                }
            }
        }

        throw new FoundException("/user/login.html");
    }
}
