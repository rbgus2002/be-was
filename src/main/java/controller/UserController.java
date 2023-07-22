package controller;

import annotation.RequestMapping;
import db.Database;
import http.HttpMethod;
import http.HttpRequest;
import http.HttpResponse;
import http.MIME;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Parser;

import java.io.IOException;
import java.util.Map;

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(path = "/user/create", method = HttpMethod.GET)
    public HttpResponse createUserByGET(HttpRequest request) throws IOException {
        Map<String, String> params = request.getParams();

        User user = new User(params.get("userId"),params.get("password"),params.get("name"),params.get("email"));
        Database.addUser(user);

        return HttpResponse.redirect("/index.html", MIME.html);
    }

    @RequestMapping(path = "/user/create", method = HttpMethod.POST)
    public HttpResponse createUserByPOST(HttpRequest request) throws IOException {
        Map<String, String> params = Parser.parseParamsFromBody(request.getBody());

        User user = new User(params.get("userId"),params.get("password"),params.get("name"),params.get("email"));
        Database.addUser(user);

        return HttpResponse.redirect("/index.html", MIME.html);
    }
}
