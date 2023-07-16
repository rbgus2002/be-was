package webserver.myframework.servlet;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.myframework.bean.annotation.Autowired;
import webserver.myframework.bean.annotation.Component;
import webserver.myframework.handler.request.RequestHandler;
import webserver.myframework.handler.request.RequestHandlerResolver;
import webserver.myframework.handler.request.exception.CannotResolveHandlerException;
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

    //TODO: HttpResponse 반환
    public void handleRequest(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            String uri = "";
            try {
                RequestHandler requestHandler =
                        requestHandlerResolver.resolveHandler(httpRequest.getUri(), httpRequest.getMethod());
                uri = requestHandler.handle(httpRequest, httpResponse);
            } catch (CannotResolveHandlerException ignored) {

            }
            if(httpResponse.getBody() == null) {
                View view = viewResolver.resolve(uri);
                httpResponse.setBody(view.render());
            }
        } catch (ReflectiveOperationException | IOException e) {
            //TODO: 예외시 처리 방법 생각
        }
    }
}
