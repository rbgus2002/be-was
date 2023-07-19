package webserver.http.response.process;

import common.util.FileUtil;
import java.io.IOException;
import java.net.URL;
import webserver.http.Headers;
import webserver.http.Http.MIME;
import webserver.http.Http.StatusCode;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.ResponseLine;

public class HtmlContentProcessStrategy implements ContentProcessStrategy {
    private static final String TEMPLATES = "/templates/";

    @Override
    public HttpResponse process(final HttpRequest httpRequest) {
        URL resource = HttpResponse.class.getResource(TEMPLATES + httpRequest.getRequestLine().getTarget().getPath());
        try {
            byte[] body = FileUtil.get(resource);
            if (resource == null) {
                return HttpResponse.notFound();
            }
            return new HttpResponse(new ResponseLine(StatusCode.OK), Headers.create(MIME.HTML), body);
        } catch (IOException exception) {
            exception.printStackTrace();
            return HttpResponse.internalError();
        }
    }
}
