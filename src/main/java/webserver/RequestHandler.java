package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import router.Router;
import webserver.model.Request;
import webserver.model.Request.Method;
import webserver.model.Response;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static http.HttpUtil.*;
import static http.HttpParser.*;
import static service.SessionService.isSessionValid;

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

        // Status Line
        String[] tokens = readSingleHTTPLine(br).split(" ");

        Method method = Method.getMethodByName(tokens[0]);
        String targetUri = tokens[1];
        String version = tokens[2].split("/")[1];

        Map<String, String> queryParameterMap = parseQueryParameter(targetUri);

        // Headers
        Map<String, String> headerMap = new HashMap<>();
        String line = readSingleHTTPLine(br).replace(" ", "");
        while(!line.equals("")) {
            tokens = line.split(":");
            headerMap.put(tokens[0], tokens[1]);
            line = readSingleHTTPLine(br).replace(" ", "");
        }
        String sid = null;
        if(headerMap.containsKey(HEADER_COOKIE)) {
            sid = headerMap.get(HEADER_COOKIE).split("=")[1];
            if(!isSessionValid(sid)) {
                sid = null;
            }
        }

        // Body
        String body = "";
        if(method == Method.PUT || method == Method.POST) {
            int contentLength = Integer.parseInt(headerMap.get(HEADER_CONTENT_LENGTH));
            char[] bodyCharacters = new char[contentLength];
            br.read(bodyCharacters);

            body = URLDecoder.decode(String.valueOf(bodyCharacters), StandardCharsets.UTF_8);
        }

        return new Request(method, version, targetUri, queryParameterMap, headerMap, sid, body);
    }

    private Response generateResponse(Request request) throws Exception {
        Response response;

        response = Router.generateResponse(request);
        if(response != null) {
            return response;
        }

        return new Response(STATUS.NOT_FOUND, null, null);
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
