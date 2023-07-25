package webserver.handlers;

import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.session.Session;
import webserver.model.Model;

public class NotFoundHandler implements Handler{
    @Override
    public HttpResponse handle(HttpRequest request, Session session, Model model) {
        return HttpResponse.notFound();
    }
}
