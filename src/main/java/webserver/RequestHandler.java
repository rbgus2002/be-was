package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class RequestHandler extends HttpHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final int CONTENT_LENGTH = 65535;
    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);

            // 요청 해석
            HttpRequest request = buildHttpRequest(in);
            logger.debug("Request Line & Headers: \n{}", request.toString());

            // 요청 수립
            HttpResponse response = new HttpResponse();

            switch (request.getRequestMethod()) {
                case GET:
                    doGet(request, response);
                    break;
                case POST:
                    doPost(request, response);
                    break;
            }

            response.response(dos);
        } catch (IOException | InvocationTargetException | IllegalAccessException e) {
            logger.error(e.getMessage());
        }
    }

    private static HttpRequest buildHttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        HttpRequest.RequestHeaderBuilder requestHeaderBuilder = new HttpRequest.RequestHeaderBuilder();
        String requestLine = br.readLine();
        requestHeaderBuilder = requestHeaderBuilder.requestLine(requestLine);

        String header;
        while (!"".equals((header = br.readLine()))) {
            requestHeaderBuilder.header(header);
        }

        // body 읽기
        if (br.ready()) {
            char[] buffer = new char[CONTENT_LENGTH];
            int read = br.read(buffer, 0, CONTENT_LENGTH);
            if (read != -1) {
                requestHeaderBuilder.body(new String(buffer, 0, read));
            } else {
                // TODO: body를 받을 수 있는 버퍼 초과
            }
        }
        return requestHeaderBuilder.build();
    }

}
