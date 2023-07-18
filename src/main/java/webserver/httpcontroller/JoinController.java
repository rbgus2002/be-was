package webserver.httpcontroller;

import db.Database;
import model.User;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.IOException;

public class JoinController extends HttpController {
    @Override
    public String process(HttpRequest request, HttpResponse response) throws IOException {
        response.setRedirect("/index.html");
        Database.addUser(new User(request.getParams()));
        for (User user : Database.findAll()) {
            logger.debug("{}", user);
        }
        return null;
    }
}
