package webserver.handlers;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public interface Handler {
    HttpResponse handle(HttpRequest request);
}
