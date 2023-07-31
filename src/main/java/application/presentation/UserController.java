package application.presentation;

import application.common.StringUtils;
import application.model.User;
import application.service.UserService;
import application.service.dto.LoginRequest;
import application.service.dto.UserRequest;
import common.annotation.Controller;
import common.annotation.HttpRequest;
import common.annotation.HttpResponse;
import common.annotation.RequestBody;
import common.annotation.RequestMapping;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import webserver.http.Http.Method;

@Controller
public class UserController {
    private final UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    @RequestMapping(value = "/user/create", method = Method.POST)
    public String create(@RequestBody final String body) {
        Map<String, String> map = StringUtils.extractBy(body);

        UserRequest userRequest = new UserRequest(
                map.get("userId"),
                map.get("name"),
                map.get("password"),
                map.get("email")
        );

        userService.create(userRequest);

        return "redirect:/index.html";
    }

    @RequestMapping(value = "/user/login", method = Method.POST)
    public String login(
            @RequestBody final String body,
            @HttpResponse final webserver.http.response.HttpResponse httpResponse
    ) {
        Map<String, String> map = StringUtils.extractBy(body);

        LoginRequest loginRequest = new LoginRequest(map.get("userId"), map.get("password"));

        if (!userService.login(loginRequest)) {
            return "redirect:/user/login_failed.html";
        }

        httpResponse.setSession(UUID.randomUUID().toString(), map.get("userId"));
        return "redirect:/index.html";
    }

    @RequestMapping(value = "/user/list.html", method = Method.GET)
    public String list(@HttpRequest final webserver.http.request.HttpRequest httpRequest) {
        Optional<String> authorizationUserId = httpRequest.getAuthorizationUserId();

        if (authorizationUserId.isEmpty()) {
            return "redirect:/user/login.html";
        }

        List<User> users = userService.getAll();

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            result.append("<tr>");
            result.append(String.format("<th scope=\"row\">%s</th>", i + 1));
            result.append(String.format("<td>%s</td>", user.getUserId()));
            result.append(String.format("<td>%s</td>", user.getName()));
            result.append(String.format("<td>%s</td>", user.getEmail()));
            result.append("<td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>");
            result.append("</tr>");
        }

        return result.toString();
    }
}
