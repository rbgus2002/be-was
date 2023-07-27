package webserver.controllers;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controllers.annotations.RequestMethod;
import webserver.controllers.annotations.RequestPath;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.Session;

import java.util.HashMap;
import java.util.Map;

import static db.Users.findAll;
import static service.SessionService.getSession;
import static webserver.http.Cookie.isValidCookie;
import static webserver.http.enums.HttpResponseStatus.FOUND;
import static webserver.http.enums.HttpResponseStatus.OK;

@RequestPath(path = "/user/list.html")
public class UserListController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(UserListController.class);

    @RequestMethod(method = "GET")
    public HttpResponse handleGet(HttpRequest request) {
        HttpResponse.Builder builder = HttpResponse.newBuilder();

        if (!isValidCookie(request.cookie())) {
            return builder
                    .version(request.version())
                    .status(FOUND)
                    .redirect("/user/login.html")
                    .build();
        }

        StringBuilder stringBuilder = new StringBuilder();
        int i = 1;
        for (User user : findAll()) {
            stringBuilder.append(String.format("<tr><th scope=\"row\">%d</th> <td>%s</td> <td>%s</td> <td>%s</td><td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td></tr>"
                    , i++, user.getUserId(), user.getName(), user.getEmail()));
        }

        Map<String, String> attributes = new HashMap<>();
        Session userSession = getSession(request.cookie().getSessionId());

        attributes.put("${logout}", "");
        attributes.put("${login}", "style=\"display:none;\"");
        attributes.put("${user}", "<li style=\"pointer-events: none;\" ><a>" + userSession.getUser().getName() + " 님</a></li>");
        attributes.put("${userList}", stringBuilder.toString());


        return builder.version(request.version())
                .status(OK)
                .fileName("src/main/resources/templates/user/list.html")
                .setAttribute(attributes)
                .build();
    }
}
