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

import static service.SessionService.getSession;
import static webserver.http.Cookie.isValidCookie;
import static webserver.http.enums.HttpResponseStatus.FOUND;
import static webserver.http.enums.HttpResponseStatus.OK;

@RequestPath(path = "/user/profile.html")
public class UserProfileController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(UserProfileController.class);

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

        Map<String, String> attributes = new HashMap<>();
        Session userSession = getSession(request.cookie().getSessionId());
        User user = userSession.getUser();

        attributes.put("${user}", "<li style=\"pointer-events: none;\" ><a>" + userSession.getUser().getName() + " 님</a></li>");
        attributes.put("${userName}", user.getName());
        attributes.put("${userEmail}", user.getEmail());

        return builder.version(request.version())
                .status(OK)
                .fileName("src/main/resources/templates/user/profile.html")
                .setAttribute(attributes)
                .build();
    }
}
