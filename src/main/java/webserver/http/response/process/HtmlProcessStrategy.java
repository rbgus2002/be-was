package webserver.http.response.process;

import common.util.FileUtil;
import java.net.URL;
import webserver.http.Headers;
import webserver.http.Http.MIME;
import webserver.http.Http.StatusCode;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.ResponseLine;

public class HtmlProcessStrategy implements ContentProcessStrategy {
    private static final String ROOT = "/templates/";

    @Override
    public HttpResponse process(final HttpRequest httpRequest) {
        try {
            URL url = getURL(ROOT, httpRequest);
            byte[] resource = FileUtil.get(url);
            if (resource == null) {
                return HttpResponse.notFound(MIME.HTML);
            }
            return new HttpResponse(new ResponseLine(StatusCode.OK), Headers.create(MIME.HTML), resource);
        } catch (Exception exception) {
            exception.printStackTrace();
            return HttpResponse.internalError(MIME.HTML);
        }
    }
}
