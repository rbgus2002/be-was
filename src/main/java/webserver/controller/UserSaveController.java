package webserver.controller;

import db.Database;
import model.User;
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

            verifyParametersCount(httpParameters);

            String userId = httpParameters.get("userId");
            String password = httpParameters.get("password");
            String name = httpParameters.get("name");
            String email = httpParameters.get("email");

            verifyEmptyParameter(userId, password, name, email);

            verifyUserIdAvailable(userId);

            User user = new User(userId, password, name, email);
            Database.addUser(user);

            httpResponse.setStatus(HttpStatus.FOUND);
            httpResponse.set(HttpField.LOCATION, "/index.html");
        } catch (BadRequestException e) {
            httpResponse.setStatus(HttpStatus.BAD_REQUEST);
        } catch (ConflictException e) {
            httpResponse.setStatus(HttpStatus.CONFLICT);
        }
    }

    private void verifyParametersCount(HttpParameters httpParameters) throws BadRequestException {
        if (httpParameters.size() != 4) {
            throw new BadRequestException();
        }
    }

    private void verifyEmptyParameter(String userId, String password, String name, String email) throws BadRequestException {
        if (userId.isEmpty() || password.isEmpty() || name.isEmpty() || email.isEmpty()) {
            throw new BadRequestException();
        }
    }

    private void verifyUserIdAvailable(String userId) throws ConflictException {
        if (Database.findUserById(userId) != null) {
            throw new ConflictException();
        }
    }
}
