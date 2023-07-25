package webserver.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.*;

class DispatcherServletTest {

    DispatcherServlet dispatcherServlet = new DispatcherServlet();

    @Test
    @DisplayName("index.html이 반환되어야 한다.")
    void index() {
        String url = "GET /index.html HTTP/1.1";
        sendRequest(url, "HTTP/1.1 200 OK");
    }

    @Test
    @DisplayName("forward 되는지?")
    void forward() {
        String url = "GET / HTTP/1.1";
        sendRequest(url, "HTTP/1.1 200 OK");
    }

    @Test
    @DisplayName("redirect 되는지?")
    void redirect() {
        String url = "GET redirect:/ HTTP/1.1";
        sendRequest(url, "HTTP/1.1 302 Found");
    }

    @Test
    @DisplayName("error 케이스")
    void error() {

        String url = "GET /ddd HTTP/1.1";
        sendRequest(url, "HTTP/1.1 404 Not Found");
    }

    private void sendRequest(String url,String expected) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        HttpResponse httpResponse = new HttpResponse();
        HttpRequest httpRequest = new HttpRequest(url, null);
        dispatcherServlet.service(httpRequest, httpResponse, dataOutputStream);
        String header = outputStream.toString().split("\r\n")[0];


        Assertions.assertEquals(expected, header);

    }

}