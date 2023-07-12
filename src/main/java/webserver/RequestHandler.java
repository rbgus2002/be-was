package webserver;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static webserver.ResponseHeader.response200Header;
import static webserver.ResponseHeader.response302Header;
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
            // 요청 해석
            RequestHeader requestHeader = buildRequestHeader(in);
            logger.debug("Request Headers: \n{}", requestHeader.getHeaders());
            logger.debug("Request Headers End");


            String url = requestHeader.getRequestUrl();
            DataOutputStream dos = new DataOutputStream(out);

            // 회원 가입
            String path = requestHeader.getRequestPath();
            Optional<Class<?>> resolve = ModelResolver.resolve(path);
            if (resolve.isPresent()) {
                Query requestQuery = requestHeader.getRequestQuery();
                Constructor<?> constructor = resolve.get().getConstructors()[0];
                Object o = constructor.newInstance(requestQuery.getValue("userId"),
                        requestQuery.getValue("password"),
                        requestQuery.getValue("name"),
                        requestQuery.getValue("email"));
                Database.addUser((User) o);
                logger.debug("새로운 유저 생성: {}", ((User) o).getUserId());
                url = "/index.html";
                response302Header(dos, url);
            }


            // 페이지 읽기
            byte[] body = readByPath(url);
            String contentType = makeContentType(url);

            // 페이지 반환
            response200Header(dos, body.length, contentType);
            responseBody(dos, body);
        } catch (IOException | ClassNotFoundException | InvocationTargetException | InstantiationException |
                IllegalAccessException e) {
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
        while (!"".equals((header = br.readLine()))) {
            requestHeaderBuilder.header(header);
        }
        return requestHeaderBuilder.build();
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
