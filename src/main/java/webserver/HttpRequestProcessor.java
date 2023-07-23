package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.exception.WeirdRequestException;
import webserver.http.HttpRequestParser;
import webserver.http.HttpResponseSender;
import webserver.http.message.*;

import java.io.*;
import java.net.Socket;


public class HttpRequestProcessor {
    public static final Logger logger = LoggerFactory.getLogger(HttpRequestProcessor.class);
    private final HttpRequestParser requestParser = new HttpRequestParser();
    private final HttpResponseSender responseSender = new HttpResponseSender();
    private final HttpRequestHandler frontHandler = new HttpRequestHandler();

    public void process(Socket connection) {
        try (InputStream inputStream = connection.getInputStream(); OutputStream outputStream = connection.getOutputStream()) {
            HttpRequest httpRequest = requestParser.parseHttpRequest(inputStream);
            logger.debug("requestMessage : {}", httpRequest);

            HttpResponse httpResponse = frontHandler.handle(httpRequest);

            responseSender.sendResponse(outputStream, httpResponse);
            logger.debug("responseMessage : {}", httpResponse);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (WeirdRequestException ignored) {}
    }
}
