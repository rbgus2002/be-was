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
import static webserver.model.Response.*;

import service.FileService;
import service.UserService;
import webserver.model.Request;
import webserver.model.Request.Method;
import webserver.model.Response;
import webserver.model.Response.STATUS;
import webserver.model.Response.MIME;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

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
    }

    private Request parseRequest(Socket connection) throws IOException, IllegalArgumentException {
        InputStream in = connection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        /*
         StartLine
         */
        String[] tokens = readSingleHTTPLine(br).split(" ");

        Method method = Method.getMethodByName(tokens[0]);
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
        String body = "";
        if(method == Method.PUT || method == Method.POST) {
            int contentLength = Integer.parseInt(headerMap.get(HEADER_CONTENT_LENGTH));
            char[] bodyCharacters = new char[contentLength];
            br.read(bodyCharacters);

            body = URLDecoder.decode(String.valueOf(bodyCharacters), StandardCharsets.UTF_8);
        }

        return new Request(method, version, targetUri, queryParameterMap, headerMap, body);
    }
    private String readSingleHTTPLine(BufferedReader br) throws IOException, NullPointerException {
        return URLDecoder.decode(br.readLine(), StandardCharsets.UTF_8);
    }
    public Map<String, String> parseQueryParameter(String route) {
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

        String[] tokens = targetUri.split("\\.");
        String extension = tokens[tokens.length-1];
        MIME mime = MIME.getMimeByExtension(extension);
        if (mime != null) {
            byte[] body = FileService.loadStaticFile(targetUri);

            Map<String, String> headerMap = new HashMap<>();
            headerMap.put(HEADER_CONTENT_TYPE, mime.getMime() + HEADER_CHARSET);
            headerMap.put(HEADER_CONTENT_LENGTH, String.valueOf(body.length));

            return new Response(STATUS.OK, HEADER_HTTP_VERSION, headerMap, body);
        }
        if(targetUri.startsWith("/user/create")) {
            UserService.userSignUp(request.getBody());

            Map<String, String> headerMap = new HashMap<>();
            headerMap.put(HEADER_REDIRECT_LOCATION, INDEX_URL);

            return new Response(STATUS.TEMPORARY_MOVED, HEADER_HTTP_VERSION, headerMap, null);
        }

        return new Response(STATUS.NOT_FOUND, HEADER_HTTP_VERSION, null, null);
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
        if(body != null) {
            dos.write(body, 0, body.length);
        }
        dos.flush();
    }
}
