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
        return new HttpResponse("/index.html");
    }

    @RequestMapping(path = "/user/create", method = HttpUtils.Method.GET)
    public HttpResponse creatUser(Map<String, String> parameters) throws FileNotFoundException {
        User newUser = ModelConverter.toUser(parameters);
        User findUser = Database.findUserById(newUser.getUserId());
        if (findUser != null) {
            return new HttpResponse("/error.html");
        }
        Database.addUser(newUser);
        return new HttpResponse("/index.html");
    }
}
