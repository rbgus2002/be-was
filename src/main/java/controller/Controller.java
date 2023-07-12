package controller;

import annotation.RequestMapping;
import http.HttpMethod;
import http.HttpResponse;

import java.io.IOException;

public class Controller {

    private Controller() {
    }

    private static class SingletonHelper {
        private static final Controller INSTANCE = new Controller();
    }

    public static Controller getInstance() {
        return SingletonHelper.INSTANCE;
    }

    @RequestMapping(path = "/index.html", method = HttpMethod.GET)
    public HttpResponse index() throws IOException {
        return new HttpResponse("/index.html");
    }
}
