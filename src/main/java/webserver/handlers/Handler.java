package webserver.handlers;

import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.session.Session;
import webserver.model.Model;

public interface Handler {
    HttpResponse handle(HttpRequest request, Session session, Model model);
}
