package webserver.server;

import java.io.*;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.response.ResponseWriter;
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
        DataOutputStream dataOutputStream = null;
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            dataOutputStream = new DataOutputStream(out);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

            String requestFirstLine = bufferedReader.readLine();
            logger.info("First Header : " + requestFirstLine);
            //todo 파싱에 넣기
            String requestHeader;
            String requestBody;

            int contentLength = 0;
            String cookie = null;
            while ((requestHeader = bufferedReader.readLine()) != null && (requestHeader.length() != 0)) {
                logger.info("Remain Header : " + requestHeader);
                if (requestHeader.startsWith(ResponseHeader.CONTENT_LENGTH.getConstant())) {
                    contentLength = Integer.parseInt(requestHeader.substring(ResponseHeader.CONTENT_LENGTH.getConstant().length() + 1).trim());
                }
                //todo Cookie Parsing하기
                if (requestHeader.startsWith("Cookie")) {
                    cookie = requestHeader;
                }
            }
            requestBody = readBody(bufferedReader, contentLength);

            HttpRequest httpRequest = new HttpRequest(requestFirstLine, requestBody, cookie);
            //todo: dispatcherServlet 싱글톤으로
            DispatcherServlet dispatcherServlet = DispatcherServlet.of();
            dispatcherServlet.service(httpRequest, new HttpResponse(cookie), dataOutputStream);
        } catch (Exception e) {
            logger.error(e.getMessage());
            ResponseWriter responseWriter = new ResponseWriter(dataOutputStream, new HttpResponse());
            responseWriter.sendBadRequest();
        }

    }

    private static String readBody(BufferedReader bufferedReader, int contentLength) throws IOException {
        String requestBody = null;
        if(contentLength != 0) {
            char[] socketBody = new char[contentLength];
            int readLength = bufferedReader.read(socketBody, 0, contentLength);
            if(readLength == contentLength) {
                requestBody = new String(socketBody);
            }
        }
        return requestBody;
    }

}
