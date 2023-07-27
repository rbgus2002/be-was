package webserver.controllers;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controllers.annotations.RequestMethod;
import webserver.controllers.annotations.RequestPath;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.Session;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static db.Users.findUserById;
import static service.SessionService.addSession;
import static webserver.http.enums.ContentType.HTML;
import static webserver.http.enums.HttpResponseStatus.BAD_REQUEST;
import static webserver.http.enums.HttpResponseStatus.FOUND;

@RequestPath(path = "/user/login")
public class UserLoginController implements Controller {
    private static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);

    @RequestMethod(method = "POST")
    public HttpResponse handlePost(HttpRequest request) {
        Map<String, String> parameters = request.body();
        if(!verifyParameter(parameters))
            return createErrorResponse(request, BAD_REQUEST);

        // todo: addUser in Service, findeUser in Database?
        User loginUser = findUserById(parameters.get("userId"));

        HttpResponse.Builder builder = HttpResponse.newBuilder();

        String path = "/user/login_failed.html";
        if(loginUser.getPassword().equals(parameters.get("password"))) {
            path = "/index.html";
            Session session = createUserSession(loginUser);
            builder.sessionId(session.getSessionId());
        }

        return builder.version(request.version())
                .status(FOUND)
                .redirect(path)
                .build();

    }

    private static Session createUserSession(User loginUser) {
        String sessionID = UUID.randomUUID().toString();
        Session session = new Session(sessionID, loginUser);
        addSession(session);
        return session;
    }

    private boolean verifyParameter(Map<String, String> parameters) {
        Set<String> essentialFields = Set.of("userId", "password");
        if(!parameters.keySet().equals(essentialFields))
            return false;

        for (String field: essentialFields) {
            if("".equals(parameters.get(field)))
                return false;
        }

        return true;
    }
}
