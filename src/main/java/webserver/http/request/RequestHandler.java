package webserver.http.request;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import webserver.http.response.HttpResponse;
import webserver.http.response.ResponseHandler;

public class RequestHandler implements Runnable {
    private final Socket connection;

    public RequestHandler(final Socket connection) {
        this.connection = connection;
    }

    public void run() {
        try (InputStream inputStream = connection.getInputStream(); OutputStream outputStream = connection.getOutputStream()) {
            HttpRequest httpRequest = HttpRequest.from(inputStream);
            HttpResponse httpResponse = HttpResponse.from(httpRequest);
            ResponseHandler.response(httpResponse, outputStream);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
