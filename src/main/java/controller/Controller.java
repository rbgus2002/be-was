package controller;

import annotation.RequestMapping;
import converter.ModelConverter;
import db.Database;
import http.HttpResponse;
import model.User;
import util.HttpUtils;

import java.io.FileNotFoundException;
import java.util.Map;

public class Controller {

    private Controller() {
    }

    private static class SingletonHelper {
        private static final Controller INSTANCE = new Controller();
    }

    public static Controller getInstance() {
        return SingletonHelper.INSTANCE;
    }

    @RequestMapping(path = "/index.html", method = HttpUtils.Method.GET)
    public HttpResponse index() throws FileNotFoundException {
        return HttpResponse.ok("/index.html");
    }

    @RequestMapping(path = "/user/form.html", method = HttpUtils.Method.GET)
    public HttpResponse userForm() throws FileNotFoundException {
        return HttpResponse.ok("/user/form.html");
    }

    @RequestMapping(path = "/user/create", method = HttpUtils.Method.GET)
    public HttpResponse creatUser(Map<String, String> parameters) throws FileNotFoundException {
        User newUser = ModelConverter.toUser(parameters);
        User findUser = Database.findUserById(newUser.getUserId());
        if (findUser != null) {
            return HttpResponse.redirect("/error.html");
        }
        Database.addUser(newUser);
        return HttpResponse.redirect("/index.html");
    }
}
