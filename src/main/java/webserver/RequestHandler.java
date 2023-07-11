package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static webserver.WebPageReader.readByPath;

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
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            RequestHeader requestHeader = buildRequestHeader(in);
            logger.debug("Request Headers: \n{}", requestHeader.getHeaders());
            logger.debug("Request Headers End");
            String url = requestHeader.getRequestUrl();
            byte[] body = readByPath(url);
            String contentType = makeContentType(url);

            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, body.length, contentType);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private static String makeContentType(String url) {
        String extension = url.substring(url.lastIndexOf("."));
        switch (extension) {
            case ".html":
                return "text/html";
            case ".css":
                return "text/css";
            case ".js":
                return "text/javascript";
            default:
                return "text/plain";
        }
    }

    private static RequestHeader buildRequestHeader(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        RequestHeader.RequestHeaderBuilder requestHeaderBuilder = new RequestHeader.RequestHeaderBuilder();
        String requestLine = br.readLine();
        requestHeaderBuilder = requestHeaderBuilder.requestLine(requestLine);

        String header;
        while(!"".equals((header = br.readLine()))){
            requestHeaderBuilder.header(header);
        }
        return requestHeaderBuilder.build();
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String contentType) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
