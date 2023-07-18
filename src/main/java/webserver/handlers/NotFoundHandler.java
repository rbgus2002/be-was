package webserver.handlers;

import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;

public class NotFoundHandler implements Handler{
    @Override
    public HttpResponse handle(HttpRequest request) {
        return HttpResponse.notFound();
    }
}
