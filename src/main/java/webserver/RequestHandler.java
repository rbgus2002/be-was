package webserver;

import common.enums.ContentType;
import common.http.HttpRequest;
import common.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.HttpRequestUtils;
import utils.HttpResponseUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // HTTP Request Message를 받아서 인스턴스화 한다.
            HttpRequest request = HttpRequestUtils.createRequest(in);
            logger.debug(request.toString());

            HttpResponse response = new HttpResponse();

            ContentType contentType = request.getRequestContentType();

            // static 폴더 내에 있는 정적 파일을 요청할 떄는 Dispatcher를 거치지 않는다.
            if (contentType.isStaticContent()) {
                response.setStaticContentResponse(
                        contentType,
                        request.getRequestPath()
                );

            }

            // HTML 파일을 요청하거나 별도로 요청하는 컨텐츠가 없을 때는 Dispatcher를 거친다.
            if (contentType.isHtmlContent() || contentType.isNoneContent()) {
                Dispatcher dispatcher = new Dispatcher();
                dispatcher.dispatch(request, response);
            }

            // HTTP Response Message를 보낸다.
            HttpResponseUtils.sendResponse(out, response);

        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);

        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}