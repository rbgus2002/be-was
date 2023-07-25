package webserver.handlers;

import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.session.Session;

public class NotFoundHandler implements Handler{
    @Override
    public HttpResponse handle(HttpRequest request, Session session) {
        return HttpResponse.notFound();
    }
}
