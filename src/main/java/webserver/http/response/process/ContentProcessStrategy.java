package webserver.http.response.process;

import common.util.FileUtil;
import java.net.URL;
import webserver.http.Headers;
import webserver.http.Http.MIME;
import webserver.http.Http.StatusCode;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.ResponseLine;

public interface ContentProcessStrategy {
    String getRoot();

    default HttpResponse process(final HttpRequest httpRequest) {
        try {
            URL url = getURL(httpRequest);
            MIME mime = httpRequest.getMIME();
            byte[] resource = FileUtil.get(url);
            if (resource == null) {
                return HttpResponse.notFound(mime);
            }
            return new HttpResponse(new ResponseLine(StatusCode.OK), Headers.create(mime, resource.length), resource);
        } catch (Exception exception) {
            exception.printStackTrace();
            return HttpResponse.internalError(httpRequest.getMIME());
        }
    }

    default URL getURL(final HttpRequest httpRequest) {
        return HttpResponse.class.getResource(getRoot() + httpRequest.getRequestLine().getTarget().getPath());
    }
}
