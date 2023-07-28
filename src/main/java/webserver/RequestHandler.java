package webserver;

import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import controller.FrontController;
import http.request.HttpRequest;
import utils.FileUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream();
             OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            HttpRequest httpRequest = HttpRequest.create(in);
            HttpResponse httpResponse = FrontController.getInstance().doDispatch(httpRequest);
            FileUtils fileUtils = new FileUtils(dos);
            fileUtils.writeHttpResponse(httpResponse);
        } catch (IOException | InvocationTargetException | IllegalAccessException e) {
            logger.error(e.getMessage());
            //TODO: NoSuchMethodException은 Method를 만드는 곳에서 예외를 발생시킬 것
        } catch (InstantiationException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
