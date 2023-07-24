package webserver.controller.user;

import db.Database;
import model.User;
import webserver.controller.Controller;
import webserver.exceptions.BadRequestException;
import webserver.exceptions.ConflictException;
import webserver.http.HttpParameters;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.utils.HttpField;
import webserver.utils.HttpParametersParser;

import java.io.IOException;

public class UserSaveController implements Controller {
    @Override
    public void process(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        try {
            HttpParameters httpParameters = HttpParametersParser.parse(httpRequest.getBody());
            String userId = httpParameters.get("userId");
            String password = httpParameters.get("password");
            String name = httpParameters.get("name");
            String email = httpParameters.get("email");

            verifyParameters(userId, password, name, email);
            addUser(userId, password, name, email);
            processResponse(httpResponse);
        } catch (BadRequestException e) {
            httpResponse.setStatus(HttpStatus.BAD_REQUEST);
        } catch (ConflictException e) {
            httpResponse.setStatus(HttpStatus.CONFLICT);
        }
    }

    private void verifyParameters(String userId, String password, String name, String email) throws BadRequestException {
        if (userId == null || password == null || name == null || email == null) {
            throw new BadRequestException();
        }
        if (userId.isEmpty() || password.isEmpty() || name.isEmpty() || email.isEmpty()) {
            throw new BadRequestException();
        }
    }

    private void addUser(String userId, String password, String name, String email) throws ConflictException {
        verifyUserId(userId);
        Database.addUser(new User(userId, password, name, email));
    }

    private void verifyUserId(String userId) throws ConflictException {
        if (Database.findUserById(userId) == null) {
            return;
        }
        throw new ConflictException();
    }

    private void processResponse(HttpResponse httpResponse) {
        httpResponse.setStatus(HttpStatus.FOUND);
        httpResponse.set(HttpField.LOCATION, "/index.html");
    }
}
