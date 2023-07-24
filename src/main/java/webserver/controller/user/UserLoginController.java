package webserver.controller.user;

import db.Database;
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
            processLoginResponse(httpResponse, userId);
        } catch (BadRequestException e) {
            httpResponse.setStatus(HttpStatus.BAD_REQUEST);
        } catch (LoginFailException e) {
            processLoginFailure(httpResponse);
        }
    }

    private void processLoginResponse(HttpResponse httpResponse, String userId) throws LoginFailException {
        String sessionId = createSession(userId);

        httpResponse.setStatus(HttpStatus.FOUND);
        httpResponse.set(HttpField.LOCATION, "/index.html");
        httpResponse.setCookie("sid=" + sessionId);
        httpResponse.setCookie("Path=/");
    }

    private void processLoginFailure(HttpResponse httpResponse) {
        httpResponse.setStatus(HttpStatus.FOUND);
        httpResponse.set(HttpField.LOCATION, "/user/login_failed.html");
    }

    private void verifyParameters(String userId, String password) throws BadRequestException {
        if (userId == null || userId.isEmpty() || password == null || password.isEmpty()) {
            throw new BadRequestException();
        }
    }

    private void authenticateUser(String userId, String password) throws LoginFailException {
        verifyUserIdExistence(userId);
        verifyCredentials(userId, password);
    }

    private void verifyUserIdExistence(String userId) throws LoginFailException {
        if (Database.findUserById(userId) == null) {
            throw new LoginFailException();
        }
    }

    private void verifyCredentials(String userId, String password) throws LoginFailException {
        if (!Database.findUserById(userId).getPassword().equals(password)) {
            throw new LoginFailException();
        }
    }

    private String createSession(String userId) {
        return SessionManager.addSession(new Session(userId));
    }
}
