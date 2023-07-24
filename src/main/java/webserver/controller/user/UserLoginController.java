package webserver.controller.user;

import db.Database;
import model.User;
import webserver.controller.Controller;
import webserver.exceptions.BadRequestException;
import webserver.exceptions.LoginFailException;
import webserver.http.HttpParameters;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.session.Session;
import webserver.session.SessionManager;
import webserver.utils.HttpField;
import webserver.utils.HttpParametersParser;

public class UserLoginController implements Controller {

    @Override
    public void process(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            HttpParameters httpParameters = HttpParametersParser.parse(httpRequest.getBody());
            String userId = httpParameters.get("userId");
            String password = httpParameters.get("password");

            verifyParameters(userId, password);
            authenticateUser(userId, password);
            processResponse(httpResponse, userId);
        } catch (BadRequestException e) {
            httpResponse.setStatus(HttpStatus.BAD_REQUEST);
        } catch (LoginFailException e) {
            httpResponse.setStatus(HttpStatus.FOUND);
            httpResponse.set(HttpField.LOCATION, "/user/login_failed.html");
        }
    }

    private void processResponse(HttpResponse httpResponse, String userId) throws LoginFailException {
        String sessionId = createSession(userId);

        httpResponse.setStatus(HttpStatus.FOUND);
        httpResponse.set(HttpField.LOCATION, "/index.html");
        httpResponse.setCookie("sid=" + sessionId);
        httpResponse.setCookie("Path=/");
    }

    private String createSession(String userId) {
        Session session = new Session(userId);
        SessionManager sessionManager = SessionManager.getInstance();

        sessionManager.addSession(session);
        return session.getSessionId();
    }

    private void authenticateUser(String userId, String password) throws LoginFailException {
        User user;

        if ((user = Database.findUserById(userId)) == null || !user.getPassword().equals(password)) {
            throw new LoginFailException();
        }
    }

    private void verifyParameters(String userId, String password) throws BadRequestException {
        if (userId == null || userId.isEmpty() || password == null || password.isEmpty()) {
            throw new BadRequestException();
        }
    }

}
