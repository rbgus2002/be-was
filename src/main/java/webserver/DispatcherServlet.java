package webserver;

import webserver.Constants.PathConstants;
import webserver.view.view.View;
import webserver.view.viewResolver.StaticViewResolver;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.Constants.HttpStatus;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Optional;

import static webserver.Constants.PathConstants.*;

public class DispatcherServlet {

    private final HttpRequest httpRequest;

    public DispatcherServlet(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public void dispatch(final DataOutputStream dos) throws IOException {

        StaticViewResolver staticViewResolver = new StaticViewResolver();
        Optional<View> view = staticViewResolver.resolve(httpRequest.getPathSegment(ROOT_PATH));

        if(view.isPresent()) view.get().render(httpRequest.getVersion(), null, dos);

        if(view.isEmpty()) {
            HttpResponse httpResponse = HttpResponse.ofWithStatusOnly(httpRequest.getVersion(), HttpStatus.NOT_FOUND);
            httpResponse.sendResponse(dos);
        }
    }
}
