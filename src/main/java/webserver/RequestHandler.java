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
import model.Session;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static http.HttpUtil.*;
import static model.User.*;
import static webserver.model.Response.*;

import service.FileService;
import service.SessionService;
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
        String line = readSingleHTTPLine(br).replace(" ", "");
        while(!line.equals("")) {
            tokens = line.split(":");
            headerMap.put(tokens[0], tokens[1]);
            line = readSingleHTTPLine(br).replace(" ", "");
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

    private Response generateResponse(Request request) throws Exception {
        String targetUri = request.getTargetUri();

        // Static Files
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
        // Service Logic
        if(targetUri.startsWith("/user/create")) {
            Map<String, String> bodyParameterMap = parseBodyParameter(request.getBody());
            UserService.userSignUp(bodyParameterMap);

            Map<String, String> headerMap = new HashMap<>();
            headerMap.put(HEADER_REDIRECT_LOCATION, INDEX_URL);

            return new Response(STATUS.TEMPORARY_MOVED, HEADER_HTTP_VERSION, headerMap, null);
        }
        if(targetUri.startsWith("/user/login")){
            Map<String, String> bodyParameterMap = parseBodyParameter(request.getBody());
            String userId = bodyParameterMap.get("userId");
            String password = bodyParameterMap.get("password");
            // ID/PW 검증
            if(!UserService.validateUser(userId, password)) {
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put(HEADER_REDIRECT_LOCATION, LOGIN_FAILED_URL);
                return new Response(STATUS.TEMPORARY_MOVED, HEADER_HTTP_VERSION, headerMap, null);
            }

            // Session ID 추가
            Session session = SessionService.getSession(userId);
            Map<String, String> headerMap = new HashMap<>();
            headerMap.put(HEADER_REDIRECT_LOCATION, INDEX_URL);
            headerMap.put(HEADER_SET_COOKIE, HEADER_SESSION_ID + session.getSessionId() + HEADER_COOKIE_PATH);

            return new Response(STATUS.TEMPORARY_MOVED, HEADER_HTTP_VERSION, headerMap, null);
        }

        return new Response(STATUS.NOT_FOUND, HEADER_HTTP_VERSION, null, null);
    }

    private void sendResponse(Response response, Socket connection) throws IOException {
        OutputStream out = connection.getOutputStream();
        DataOutputStream dos = new DataOutputStream(out);

        // StatusLine
        STATUS status = response.getStatus();
        dos.writeBytes(HEADER_HTTP + response.getVersion() + " " +
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
