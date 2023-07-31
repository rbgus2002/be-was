package webserver.http.response;

import common.util.FileUtil;
import java.net.URL;
import webserver.http.Http.MIME;
import webserver.http.request.HttpRequest;

public interface ContentProcessStrategy {
    String getRoot();

    default HttpResponse process(final HttpRequest httpRequest) {
        HttpResponse result = HttpResponse.init();
        try {
            URL url = getURL(httpRequest);
            MIME mime = httpRequest.getMIME();
            byte[] resource = FileUtil.get(url);
            if (resource == null) {
                result.notFound(mime);
            } else {
                result.ok(mime, resource);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            result.internalError(httpRequest.getMIME());
        }
        return result;
    }

    default URL getURL(final HttpRequest httpRequest) {
        return HttpResponse.class.getResource(getRoot() + httpRequest.getRequestLine().getTarget().getPath());
    }
}
