package webserver;

import model.User;

import java.io.IOException;

public class JoinHandler extends HttpHandler {
    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        response.setMethod("303");
        response.setStatusMessage("See Other");
        response.setHeader("Location", "/index.html");
        User user = new User(request.getParams());
        logger.debug("{}", user);
        response.send();
    }
}
