package webserver.handlers;

import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.session.Session;

public interface Handler {
    HttpResponse handle(HttpRequest request, Session session);
}
