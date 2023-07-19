package webserver.handlers;

import model.User;
import service.UserService;
import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.http.message.URL;

import java.util.List;
import java.util.Map;

public class UserJoinHandler implements Handler {
    private final UserService userService;

    public UserJoinHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public HttpResponse handle(HttpRequest request) {
        URL url = request.getURL();
        Map<String, List<String>> parameters = url.getQueryParameter();
        User user = mapUserFrom(parameters);
        userService.join(user);
        return HttpResponse.created();
    }

    private static User mapUserFrom(Map<String, List<String>> parameters) {
        String userId = parameters.get("userId").get(0);
        String password = parameters.get("password").get(0);
        String name = parameters.get("name").get(0);
        String email = parameters.get("email").get(0);
        return new User(userId, password, name, email);
    }
}
