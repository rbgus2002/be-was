package webserver;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static model.User.*;

import webserver.model.Request;
import webserver.model.Request.Method;
import webserver.model.Response;
import webserver.model.Response.STATUS;
import webserver.model.Response.MIME;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private static final String STATIC_FILEPATH = "./src/main/resources/static";
    private static final String TEMPLATE_FILEPATH = "./src/main/resources/templates";

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}",
                connection.getInetAddress(),
                connection.getPort());

        try(connection) {
            // Request
            Request request = parseRequest(connection);

            // Response
            Response response = generateResponse(request);
            sendResponse(response, connection);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        finally {
            try {
                connection.close();
                logger.debug("Client disconnected : IP: {}, PORT:{}",
                        connection.getInetAddress(),
                        connection.getPort());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Request parseRequest(Socket connection) throws IOException, IllegalArgumentException {
        InputStream in = connection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        /*
         StartLine
         */
        String[] tokens = readSingleHTTPLine(br).split(" ");

        Method method = Method.valueOf(tokens[0]);
        String targetUri = tokens[1];
        String version = tokens[2].split("/")[1];

        Map<String, String> queryParameterMap = parseQueryParameter(targetUri);

        /*
         Headers
         */
        Map<String, String> headerMap = new HashMap<>();
        String line = readSingleHTTPLine(br);
        while(!line.equals("")) {
            tokens = line.split(": ");
            headerMap.put(tokens[0], tokens[1]);
            line = readSingleHTTPLine(br);
        }

        /*
         Body
         */
        StringBuilder sb = new StringBuilder();
        if(method == Method.PUT || method == Method.POST) {
            line = readSingleHTTPLine(br);
            while(!line.equals("")) {
                sb.append(line);
            }
        }
        String body = sb.toString();

        return new Request(method, version, targetUri, queryParameterMap, headerMap, body);
    }
    private String readSingleHTTPLine(BufferedReader br) throws IOException {
        return URLDecoder.decode(br.readLine(), StandardCharsets.UTF_8);
    }
    private Map<String, String> parseQueryParameter(String route) {
        // ?를 기준으로 쿼리 스트링 분할
        String[] tokens = route.split("\\?");
        if(tokens.length < 2) {
            return null;
        }
        String queryString = tokens[1];
        // &를 기준으로 파라미터 분할
        String[] queryParameterList = queryString.split("&");
        // Map에 key-value 저장
        Map<String, String> queryParameterMap = new HashMap<>();
        for(String queryParameter: queryParameterList) {
            queryParameterMap.put(queryParameter.split("=")[0],
                    queryParameter.split("=")[1]);
        }

        return queryParameterMap;
    }

    private Response generateResponse(Request request) throws Exception {
        String targetUri = request.getTargetUri();

        if (isStaticFile(targetUri)) {
            String[] tokens = targetUri.split("\\.");
            String extension = tokens[tokens.length-1];
            byte[] body = loadStaticFile(targetUri);

            Map<String, String> headerMap = new HashMap<>();
            headerMap.put("Content-Type", MIME.getMimeByExtension(extension).getMime() + ";charset=utf-8");
            headerMap.put("Content-Length", String.valueOf(body.length));

            return new Response(STATUS.OK, "1.1", headerMap, body);
        }
        if(targetUri.startsWith("/user/create")) {
            userSignUp(request.getQueryParameterMap());

            Map<String, String> headerMap = new HashMap<>();
            headerMap.put("Content-Type", MIME.HTML.getMime() + ";charset=utf-8");
            headerMap.put("Content-Length", String.valueOf(0));

            return new Response(STATUS.CREATED,"1.1", headerMap, null);
        }

        return null;
    }
    public boolean isStaticFile(String targetUri) {
        return Arrays.stream(MIME.values())
                .anyMatch(mime -> targetUri.endsWith("." + mime.toString()));
    }

    public byte[] loadStaticFile(String route) throws IOException {
        // 요청 경로의 파일을 반환
        File f;
        byte[] body;
        // 두 가지의 경로 모두를 조회해야 합니다.
        if (!(f = new File(STATIC_FILEPATH + route)).exists()) {
            f = new File(TEMPLATE_FILEPATH + route);
        }
        body = Files.readAllBytes(f.toPath());
        return body;
    }
    public void userSignUp(Map<String, String> queryParameterMap) throws NullPointerException {
        // User 객체 생성
        User user = new User(queryParameterMap.get(USERID),
                queryParameterMap.get(PASSWORD),
                queryParameterMap.get(NAME),
                queryParameterMap.get(EMAIL));
        // DB 저장
        Database.addUser(user);
    }

    private void sendResponse(Response response, Socket connection) throws IOException {
        OutputStream out = connection.getOutputStream();
        DataOutputStream dos = new DataOutputStream(out);

        // StatusLine
        STATUS status = response.getStatus();
        dos.writeBytes("HTTP/" + response.getVersion() + " " +
                status.getStatusCode() + " " + status.getStatusMessage() + "\r\n");
        // Headers
        for (Map.Entry<String, String> entry : response.getHeaderMap().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            dos.writeBytes(key + ": " + value + "\r\n");
        }
        dos.writeBytes("\r\n");
        // Body
        byte[] body = response.getBody();
        dos.write(body, 0, body.length);
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
