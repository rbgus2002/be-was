package webserver.server;

import java.io.*;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.header.ResponseHeader;

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
            DataOutputStream dataOutputStream = new DataOutputStream(out);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

            String requestFirstLine = bufferedReader.readLine();
            logger.info("First Header : " + requestFirstLine);

            String requestHeader;
            String requestBody = null;
            int contentLength = 0;
            while ((requestHeader = bufferedReader.readLine()) != null && (requestHeader.length() != 0)) {
                logger.info("Remain Header : " + requestHeader);
                if (requestHeader.startsWith(ResponseHeader.CONTENT_LENGTH.getConstant())) {
                    contentLength = Integer.parseInt(requestHeader.substring(ResponseHeader.CONTENT_LENGTH.getConstant().length() + 1).trim());
                }
            }
            if(contentLength != 0) {
                char[] socketBody = new char[contentLength];
                int readLength = bufferedReader.read(socketBody, 0, contentLength);
                if(readLength == contentLength) {
                    requestBody = new String(socketBody);
                    logger.info("Body : " + requestBody);
                }
            }

            HttpRequest httpRequest = new HttpRequest(requestFirstLine, requestBody);

            DispatcherServlet dispatcherServlet = new DispatcherServlet();
            dispatcherServlet.service(httpRequest, new HttpResponse(), dataOutputStream);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }
}
