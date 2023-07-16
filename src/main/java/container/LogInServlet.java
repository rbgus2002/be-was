package container;

import db.Database;
import model.User;
import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class LogInServlet implements Servlet {
    @Override
    public void service(HTTPServletRequest request, HTTPServletResponse response) throws IOException {
        ConcurrentHashMap<String, String> query = request.getQuery();
        User user = new User(query.get("userId"), query.get("password"), query.get("name"), query.get("email"));
        Database.addUser(user);
        response.forward(request.getUri());
    }
}
