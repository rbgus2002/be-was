package webserver;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    @Override
    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DispatcherServlet dispatcherServlet = new DispatcherServlet();
            dispatcherServlet.doService(in, out);
        } catch (IOException e){
            // TODO : Redirect 로직 다시 구성 (여기서 리다이렉트 시킬 수 있게 하기)
        }
        catch (Throwable e) {
            logger.error(e.getMessage());
        }
    }
}
