package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static model.User.*;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String STATIC_FILEPATH = "./src/main/resources/static";
    private static final String TEMPLATE_FILEPATH = "./src/main/resources/templates";

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // Request
            // http 메세지를 저장하기 위한 StringBuilder 생성
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = URLDecoder.decode(br.readLine(), "UTF-8");
            logger.debug("request: {}", line);
            // 경로 parsing
            String route = line.split(" ")[1];
            logger.debug("route: {}", route);
            // 나머지 확인
            while(!line.equals("")) {
                line = URLDecoder.decode(br.readLine(), "UTF-8");
                sb.append(line);
                sb.append("\r\n");
                logger.debug("header: {}", line);
            }

            // Response
            DataOutputStream dos = new DataOutputStream(out);
            routeRequest(route, dos);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private void routeRequest(String route, DataOutputStream dos) throws Exception {
        if (route.endsWith(".html")) {
            serveStaticFile(route, dos);
        }
        else if(route.startsWith("/user/create")) {
            userSingUp(route, dos);
        }
    }

    private void userSingUp(String route, DataOutputStream dos) {
        // ?를 기준으로 쿼리 스트링 분할
        String queryString = route.split("\\?")[1];
        // &를 기준으로 파라미터 분할
        String[] queryParameterList = queryString.split("&");
        // Map에 key-value 저장
        Map<String, String> queryParameterMap = new HashMap<>();
        for(String queryParameter: queryParameterList) {
            queryParameterMap.put(queryParameter.split("=")[0],
                    queryParameter.split("=")[1]);
        }
        // User 객체 생성
        User user = new User(queryParameterMap.get(USERID),
                queryParameterMap.get(PASSWORD),
                queryParameterMap.get(NAME),
                queryParameterMap.get(EMAIL));

        Database.addUser(user);
    }

    private void serveStaticFile(String route, DataOutputStream dos) throws IOException {
        // 요청 경로의 파일을 반환
        File f;
        byte[] body;
        // 두 가지의 경로 모두를 조회해야 합니다.
        if (!(f = new File(STATIC_FILEPATH + route)).exists()) {
            f = new File(TEMPLATE_FILEPATH + route);
        }
        body = Files.readAllBytes(f.toPath());
        response200Header(dos, body.length);
        responseBody(dos, body);
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
