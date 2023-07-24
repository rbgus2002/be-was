package webserver.myframework.servlet;

import webserver.http.ContentType;
import webserver.http.request.HttpRequest;
import webserver.http.response.Cookie;
import webserver.http.response.HttpResponse;
import webserver.http.response.HttpStatus;
import webserver.myframework.bean.annotation.Autowired;
import webserver.myframework.bean.annotation.Component;
import webserver.myframework.handler.request.RequestHandler;
import webserver.myframework.handler.request.RequestHandlerResolver;
import webserver.myframework.handler.request.exception.NotMatchedMethodException;
import webserver.myframework.handler.request.exception.NotMatchedUriException;
import webserver.myframework.session.Session;
import webserver.myframework.view.View;
import webserver.myframework.view.ViewResolver;

import java.io.IOException;

@Component
public class DispatcherServlet {
    private final RequestHandlerResolver requestHandlerResolver;
    private final ViewResolver viewResolver;
    private static final String ERROR_URI = "/errors/";

    @Autowired
    public DispatcherServlet(RequestHandlerResolver requestHandlerResolver,
                             ViewResolver viewResolver) {
        this.requestHandlerResolver = requestHandlerResolver;
        this.viewResolver = viewResolver;
    }

    public void handleRequest(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        String uri = httpRequest.getUri();
        try {
            RequestHandler requestHandler =
                    requestHandlerResolver.resolveHandler(httpRequest.getUri(), httpRequest.getMethod());
            requestHandler.handle(httpRequest, httpResponse);
            uri = httpResponse.getUri();
        } catch (NotMatchedUriException notMatchedUriException) {
            httpResponse.setStatus(HttpStatus.NOT_FOUND);
        } catch (NotMatchedMethodException notMatchedMethodException) {
            httpResponse.setStatus(HttpStatus.METHOD_NOT_ALLOW);
        }

        setSession(httpRequest, httpResponse);
        HttpStatus httpResponseStatus = httpResponse.getStatus();
        if (httpResponseStatus.getStatusNumber() >= 400 &&
            !httpResponseStatus.equals(HttpStatus.NOT_FOUND)) {
            uri = ERROR_URI + httpResponse.getStatus().getStatusNumber() + ".html";
        }

        if (!uri.equals(HttpResponse.NOT_RENDER_URI)) {
            View view = viewResolver.resolve(uri);
            httpResponse.setStatus(HttpStatus.OK);
            httpResponse.setContentType(ContentType.getContentType(view.getFileExtension()));
            httpResponse.setBody(view.render());
        }
    }

    private static void setSession(HttpRequest httpRequest, HttpResponse httpResponse) {
        Session session = httpRequest.getSession(false);
        if(session != null) {
            Cookie cookie = new Cookie(Session.SESSION_KEY, session.getSessionId());
            httpResponse.addCookie(cookie);
        }
    }
}
