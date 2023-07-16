package webserver;

import java.io.FileNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.myframework.bean.BeanContainer;
import webserver.myframework.bean.BeanInitializer;
import webserver.myframework.bean.BeanInitializerImpl;
import webserver.myframework.bean.DefaultBeanContainer;
import webserver.myframework.bean.exception.BeanConstructorException;
import webserver.myframework.bean.exception.BeanException;
import webserver.myframework.bean.exception.BeanNotFoundException;
import webserver.myframework.handler.request.RequestHandlerInitializerImpl;
import webserver.myframework.handler.request.RequestHandlerResolver;
import webserver.myframework.handler.request.exception.RequestHandlerException;
import webserver.myframework.servlet.DispatcherServlet;

public class WebServer {
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) throws Exception {
        int port = getPort(args);
        BeanContainer beanContainer = initializeFramework();
        DispatcherServlet dispatcherServlet = (DispatcherServlet) beanContainer.findBean(DispatcherServlet.class);

        ExecutorService executorService = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors());

        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                executorService.execute(new RequestHandler(connection, dispatcherServlet));
            }
        }
    }

    private static BeanContainer initializeFramework() throws BeanException, ReflectiveOperationException, FileNotFoundException, RequestHandlerException {
        BeanContainer beanContainer = new DefaultBeanContainer();
        BeanInitializer beanInitializer = new BeanInitializerImpl(beanContainer);
        beanInitializer.initialize("");

        RequestHandlerResolver requestHandlerResolver =
                (RequestHandlerResolver) beanContainer.findBean(RequestHandlerResolver.class);
        new RequestHandlerInitializerImpl(beanContainer, requestHandlerResolver).initialize();
        return beanContainer;
    }

    private static int getPort(String[] args) {
        int port = DEFAULT_PORT;
        if (args != null && args.length != 0) {
            port = Integer.parseInt(args[0]);
        }
        return port;
    }
}
