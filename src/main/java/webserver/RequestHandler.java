package webserver;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.DataModelResolver;
import support.DataModelWrapper;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

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
            DataOutputStream dos = new DataOutputStream(out);

            // 요청 해석
            RequestHeader requestHeader = buildRequestHeader(in);
            logger.debug("Request Headers: \n{}", requestHeader.getHeaders());
            logger.debug("Request Headers End");
            String url = requestHeader.getRequestUrl();

            // 요청 수립
            String path = requestHeader.getRequestPath();
            DataModelWrapper resolve = DataModelResolver.resolve(path);
            if (resolve != null) {
                Query requestQuery = requestHeader.getRequestQuery();
                Object dataModel = resolve.constructClass(requestQuery);

                //TODO: 현재 오직 유저 생성 요청만 받으므로 이를 개선할 필요성이 있다.
                if (dataModel instanceof User)
                    url = createUser(dos, (User) dataModel);
            }


            // 페이지 읽기
            byte[] body = readByPath(url);
            String contentType = makeContentType(url);

            // 페이지 반환
            response200Header(dos, body.length, contentType);
            responseBody(dos, body);
        } catch (IOException | ClassNotFoundException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException | InstantiationException e) {
            logger.error(e.getMessage());
        }
    }

    private static String createUser(DataOutputStream dos, User o) {
        String url = "/index.html";
        Database.addUser(o);
        logger.debug("새로운 유저 생성: {}", o.getUserId());
        response302Header(dos, url);
        return url;
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
