package application.presentation;

import application.model.User;
import application.service.UserService;
import common.annotation.Controller;
import common.annotation.HttpRequest;
import common.annotation.RequestMapping;
import java.util.Optional;
import webserver.http.Http.Method;

@Controller
public class HomeController {
    private final UserService userService;

    public HomeController() {
        this.userService = new UserService();
    }

    @RequestMapping(value = "/index.html", method = Method.GET)
    public String home(@HttpRequest webserver.http.request.HttpRequest httpRequest) {
        Optional<String> authorizationUserId = httpRequest.getAuthorizationUserId();
        if (authorizationUserId.isEmpty()) {
            return "<a href=\"user/login.html\" role=\"button\">로그인</a>";
        }
        Optional<User> optionalUser = userService.findBy(authorizationUserId.get());
        if (optionalUser.isEmpty()) {
            return "<a href=\"user/login.html\" role=\"button\">로그인</a>";
        }
        return "<a href=\"#\" role=\"button\">" + optionalUser.get().getName() + "</a>";
    }
}
