package webserver.httpcontroller;

import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.IOException;

public class HomeController extends HttpController {
    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        response.setBody("Hello, World".getBytes(), "txt");
        response.send();
    }
}
