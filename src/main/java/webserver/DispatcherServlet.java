package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.InputStream;
import java.io.OutputStream;

public class DispatcherServlet {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    protected void doService(HttpRequest httpRequest, OutputStream out) {
        logger.debug("request: {}", httpRequest);
        doDispatch(httpRequest, out);
    }

    protected void doDispatch(HttpRequest httpRequest, OutputStream out) {

    }
}
