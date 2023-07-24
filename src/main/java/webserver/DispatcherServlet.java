package webserver;

import webserver.view.view.View;
import webserver.view.viewResolver.StaticViewResolver;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.Constants.HttpStatus;

import java.io.DataOutputStream;
import java.io.IOException;

public class DispatcherServlet {

    private final HttpRequest httpRequest;

    public DispatcherServlet(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public void dispatch(final DataOutputStream dos) throws IOException {

        StaticViewResolver staticViewResolver = new StaticViewResolver();
        View view = staticViewResolver.resolve(httpRequest.getFullPath());

        view.render(httpRequest.getVersion(), null, dos);

        HttpResponse httpResponse = HttpResponse.ofWithStatusOnly(httpRequest.getVersion(), HttpStatus.NOT_FOUND);
        httpResponse.sendResponse(dos);
    }
}
