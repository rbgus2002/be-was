package webserver.httpcontroller;

import db.Database;
import model.User;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.IOException;

public class JoinController extends HttpController {
    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        response.setMethod("303");
        response.setStatusMessage("See Other");
        response.setHeader("Location", "/index.html");
        Database.addUser(new User(request.getParams()));
        for (User user : Database.findAll()) {
            logger.debug("{}", user);
        }
        response.send();
    }
}
