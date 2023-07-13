package controller;

import annotation.RequestMapping;
import http.HttpResponse;
import util.HttpUtils;

import java.io.FileNotFoundException;

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
}
