package webserver;

import view.view.View;
import view.viewResolver.StaticViewResolver;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Optional;

public class DispatcherServlet {

    private final HttpRequest httpRequest;

    public DispatcherServlet(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public void dispatch(DataOutputStream dos) throws IOException {

        StaticViewResolver staticViewResolver = new StaticViewResolver();
        Optional<View> view = staticViewResolver.resolve(httpRequest.getPath());

        if(view.isPresent()) view.get().render(null, dos);

        if(view.isEmpty()) {
            byte[] body = "404 Not Found".getBytes();
            HttpResponse httpResponse = HttpResponse.of(HttpStatus.NOT_FOUND, ContentType.HTML, body);
            httpResponse.sendResponse(dos);
        }
    }
}
