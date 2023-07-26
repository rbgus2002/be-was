package webserver.controller.user;

import db.SessionDatabase;
import db.UserDatabase;
import webserver.controller.Controller;
import webserver.exceptions.BadRequestException;
import webserver.exceptions.LoginFailException;
import webserver.http.HttpParameters;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.session.Session;
import webserver.utils.CookieConstants;
import webserver.utils.Location;

public class UserLoginController implements Controller {

    @Override
    public void process(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            HttpParameters httpParameters = httpRequest.getParameters();
            String userId = httpParameters.get("userId");
            String password = httpParameters.get("password");

            verifyParameters(userId, password);
            authenticateUser(userId, password);
            processLoginResponse(httpResponse, userId);
        } catch (BadRequestException e) {
            httpResponse.setStatus(HttpStatus.BAD_REQUEST);
        } catch (LoginFailException e) {
            httpResponse.sendRedirect(Location.LOGIN_FAIL_PAGE);
        }
    }

    private void processLoginResponse(HttpResponse httpResponse, String userId) throws LoginFailException {
        String sessionId = createSession(userId);

        httpResponse.setCookie(CookieConstants.SESSION_ID, sessionId);
        httpResponse.setCookie(CookieConstants.PATH, "/");
        httpResponse.sendRedirect(Location.INDEX_PAGE);
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
        if (UserDatabase.findUserById(userId) == null) {
            throw new LoginFailException();
        }
    }

    private void verifyCredentials(String userId, String password) throws LoginFailException {
        if (!UserDatabase.findUserById(userId).getPassword().equals(password)) {
            throw new LoginFailException();
        }
    }

    private String createSession(String userId) {
        return SessionDatabase.addSession(new Session(userId));
    }
}
