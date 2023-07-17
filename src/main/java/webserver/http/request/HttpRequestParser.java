package webserver.http.request;

import webserver.http.request.exception.IllegalRequestParameterException;

import java.io.IOException;
import java.io.InputStream;

public interface HttpRequestParser {
    HttpRequest parse(InputStream inputStream) throws IOException, IllegalRequestParameterException;
}
