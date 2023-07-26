package webserver.controller.user;

import db.UserDatabase;
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
import webserver.utils.Location;

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
            addNewUser(userId, password, name, email);
            redirectToIndexPage(httpResponse);
        } catch (BadRequestException e) {
            httpResponse.setStatus(HttpStatus.BAD_REQUEST);
        } catch (ConflictException e) {
            httpResponse.setStatus(HttpStatus.CONFLICT);
        }
    }

    private void verifyParameters(String userId, String password, String name, String email) throws BadRequestException {
        checkNullOrBlank(userId);
        checkNullOrBlank(password);
        checkNullOrBlank(name);
        checkNullOrBlank(email);
    }

    private void checkNullOrBlank(String parameter) throws BadRequestException {
        if(parameter == null || parameter.isBlank()) {
            throw new BadRequestException();
        }
    }

    private void addNewUser(String userId, String password, String name, String email) throws ConflictException {
        checkUserIdNotExists(userId);
        UserDatabase.addUser(new User(userId, password, name, email));
    }

    private void checkUserIdNotExists(String userId) throws ConflictException {
        if (UserDatabase.findUserById(userId) != null) {
            throw new ConflictException();
        }
    }

    private void redirectToIndexPage(HttpResponse httpResponse) {
        httpResponse.setStatus(HttpStatus.FOUND);
        httpResponse.set(HttpField.LOCATION, Location.INDEX_PAGE);
    }
}
