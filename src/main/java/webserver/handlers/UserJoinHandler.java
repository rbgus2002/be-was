package webserver.handlers;

import exception.UserServiceException;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.UserService;
import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.session.Session;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class UserJoinHandler implements Handler {
    public static final Logger logger = LoggerFactory.getLogger(UserJoinHandler.class);

    public static final String REDIRECT_URL = "/index.html";
    public static final String EMAIL = "email";
    public static final String NAME = "name";
    public static final String PASSWORD = "password";
    public static final String USER_ID = "userId";
    public static final String EQUAL = "=";
    public static final String AND = "&";

    private final UserService userService;

    public UserJoinHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public HttpResponse handle(HttpRequest request, Session session) {
        try {
            char[] messageBody = request.getBody();
            String body = makeString(messageBody);
            User user = mapToUserFrom(body);
            userService.join(user);
            return HttpResponse.redirect(REDIRECT_URL);
        } catch (IllegalArgumentException e) {
            logger.warn("bad request : {}", e.getMessage());
            return HttpResponse.badRequest();
        } catch (UserServiceException e) {
            logger.warn("duplicate userId : {}", e.getMessage());
            return HttpResponse.badRequest();
        }
    }

    private String makeString(char[] messageBody) {
        String body = String.valueOf(messageBody);
        return URLDecoder.decode(body, StandardCharsets.UTF_8);
    }

    private static User mapToUserFrom(String body) {
        try {
            Map<String, String> joinInfos = parseBody(body);
            String userId = joinInfos.get(USER_ID);
            String password = joinInfos.get(PASSWORD);
            String name = joinInfos.get(NAME);
            String email = joinInfos.get(EMAIL);
            return new User(userId, password, name, email);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private static Map<String, String> parseBody(String body) {
        HashMap<String, String> joinInfos = new HashMap<>();
        String[] infos = body.split(AND);
        for (String info : infos) {
            String[] token = info.split(EQUAL);
            joinInfos.put(token[0], token[1]);
        }
        return joinInfos;
    }
}
