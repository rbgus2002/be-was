package webserver.http.response.process;

import java.net.URL;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public interface ContentProcessStrategy {
    HttpResponse process(HttpRequest httpRequest);

    default URL getURL(final String root, final HttpRequest httpRequest) {
        return HttpResponse.class.getResource(root + httpRequest.getRequestLine().getTarget().getPath());
    }
}
