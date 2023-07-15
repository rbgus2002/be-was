package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.StringUtils;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    public static final String RESOURCE_PATH = "src/main/resources/templates";

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String startLine = bufferedReader.readLine();
            logger.debug("request start header line : {}", startLine);

            String requestHeader = readRequestHeader(bufferedReader);
            logger.debug("request header : {}", requestHeader);

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = Files.readAllBytes(new File(RESOURCE_PATH + findRequestPath(startLine)).toPath());

            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String readRequestHeader(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        StringBuilder stringBuilder = new StringBuilder(line);
        while (!line.equals("")) {
            stringBuilder.append(StringUtils.NEWLINE).append(line);
            line = bufferedReader.readLine();
        }
        return stringBuilder.toString();
    }

    private String findRequestPath(String startHeaderLine) {
        String[] tokens = startHeaderLine.split(" ");

        return tokens[1];
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
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
