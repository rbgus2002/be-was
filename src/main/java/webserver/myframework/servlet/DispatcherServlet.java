package webserver.myframework.servlet;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;
import webserver.myframework.bean.annotation.Autowired;
import webserver.myframework.bean.annotation.Component;
import webserver.myframework.requesthandler.RequestHandler;
import webserver.myframework.requesthandler.RequestHandlerResolver;
import webserver.myframework.requesthandler.exception.CannotResolveHandlerException;
import webserver.myframework.view.View;
import webserver.myframework.view.ViewResolver;

import java.io.IOException;

@Component
public class DispatcherServlet {
    private final RequestHandlerResolver requestHandlerResolver;
    private final ViewResolver viewResolver;

    @Autowired
    public DispatcherServlet(RequestHandlerResolver requestHandlerResolver,
                             ViewResolver viewResolver) {
        this.requestHandlerResolver = requestHandlerResolver;
        this.viewResolver = viewResolver;
    }

    public void handleRequest(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            String uri = httpRequest.getUri();
            try {
                RequestHandler requestHandler =
                        requestHandlerResolver.resolveHandler(httpRequest.getUri(), httpRequest.getMethod());
                requestHandler.handle(httpRequest, httpResponse);
                uri = httpResponse.getUri();
            } catch (CannotResolveHandlerException ignored) {

            } catch (Exception exception) {
                httpResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            if(httpResponse.getStatus().getStatusNumber() >= 400) {
                uri = "/errors/" + httpResponse.getStatus().getStatusNumber();
            }

            if(!uri.equals(HttpResponse.NOT_RENDER_URI)) {
                View view = viewResolver.resolve(uri);
                httpResponse.setBody(view.render());
            }
        } catch (IOException e) {
            //TODO: 예외시 처리 방법 생각
        }
    }
}
