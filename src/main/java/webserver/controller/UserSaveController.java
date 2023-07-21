package webserver.controller;

import db.Database;
import model.User;
import webserver.exceptions.BadRequestException;
import webserver.exceptions.ConflictException;
import webserver.http.HttpParameters;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;

public class UserSaveController {
    public void process(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            HttpParameters parametersMap = httpRequest.getParameters();

            verifyParametersCount(parametersMap);

            String userId = (String) parametersMap.get("userId");
            String password = (String) parametersMap.get("password");
            String name = (String) parametersMap.get("name");
            String email = (String) parametersMap.get("email");

            verifyEmptyParameter(userId, password, name, email);

            verifyUserIdConflict(userId);

            User user = new User(userId, password, name, email);
            Database.addUser(user);

            httpResponse.setStatus(HttpStatus.OK);
        } catch (BadRequestException e) {
            httpResponse.setStatus(HttpStatus.BAD_REQUEST);
        } catch (ConflictException e) {
            httpResponse.setStatus(HttpStatus.CONFLICT);
        } finally {
            httpResponse.setContentType("application/json");
            httpResponse.setContentLength(0);
        }
    }

    private void verifyParametersCount(HttpParameters parametersMap) throws BadRequestException {
        if (parametersMap.size() != 4) {
            throw new BadRequestException();
        }
    }

    private void verifyEmptyParameter(String userId, String password, String name, String email) throws BadRequestException {
        if (userId.isEmpty() || password.isEmpty() || name.isEmpty() || email.isEmpty()) {
            throw new BadRequestException();
        }
    }

    private void verifyUserIdConflict(String userId) throws ConflictException {
        if (Database.findUserById(userId) != null) {
            throw new ConflictException();
        }
    }
}
