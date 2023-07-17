package webserver;

import java.io.IOException;

public class HomeHandler extends HttpHandler {
    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        response.setBody("Hello, World".getBytes(), "txt");
        response.send();
    }
}
