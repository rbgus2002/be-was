package webserver.handlers;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

public class NotFoundHandler implements Handler{
    @Override
    public HttpResponse handle(HttpRequest request) {
        return HttpResponse.notFound();
    }
}
