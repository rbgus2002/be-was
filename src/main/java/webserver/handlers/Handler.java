package webserver.handlers;

import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;

public interface Handler {
    HttpResponse handle(HttpRequest request);
}
