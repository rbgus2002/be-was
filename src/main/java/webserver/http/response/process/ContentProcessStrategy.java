package webserver.http.response.process;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public interface ContentProcessStrategy {
    HttpResponse process(HttpRequest httpRequest);
}
