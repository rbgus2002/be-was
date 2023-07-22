package webserver.handlers;

import model.User;
import service.UserService;
import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class UserJoinHandler implements Handler {
    private final UserService userService;

    public UserJoinHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public HttpResponse handle(HttpRequest request) {
        String body = URLDecoder.decode(request.getBody(), StandardCharsets.UTF_8);
        User user = mapToUserFrom(body);
        userService.join(user);
        return HttpResponse.created("/index.html");
    }

    private static User mapToUserFrom(String body) {
        Map<String, String> joinInfos = parseBody(body);
        String userId = joinInfos.get("userId");
        String password = joinInfos.get("password");
        String name = joinInfos.get("name");
        String email = joinInfos.get("email");
        return new User(userId, password, name, email);
    }

    private static Map<String, String> parseBody(String body) {
        HashMap<String, String> joinInfos = new HashMap<>();
        String[] infos = body.split("&");
        for (String info : infos) {
            String[] token = info.split("=");
            joinInfos.put(token[0], token[1]);
        }
        return joinInfos;
    }
}
