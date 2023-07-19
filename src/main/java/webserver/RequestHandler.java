package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpConstant;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.utils.FileUtils;

import java.io.*;
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

        try (BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
             OutputStream out = connection.getOutputStream()) {
            logRequestMessageAndResetStream(in);
            HttpRequest httpRequest = new HttpRequest(in);
            HttpResponse httpResponse = new HttpResponse();

            FileUtils.processFileResponse(httpRequest.getURI(), httpResponse);

            sendResponse(httpResponse, out);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void logRequestMessageAndResetStream(BufferedInputStream in) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String line;

        in.mark(0);

        while (bufferedReader.ready()) {
            line = bufferedReader.readLine();
            stringBuilder.append(line).append('\n');
        }

        in.reset();

        logger.debug("{}", stringBuilder.toString());
    }

    private void sendResponse(HttpResponse httpResponse, OutputStream out) throws IOException {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(out);

        bufferedOutputStream.write(httpResponse.getHeaderBytes());
        bufferedOutputStream.write(HttpConstant.CRLF.getBytes());
        if (!httpResponse.isBodyEmpty()) {
            bufferedOutputStream.write(httpResponse.getBodyBytes());
        }
        bufferedOutputStream.flush();
    }
}
