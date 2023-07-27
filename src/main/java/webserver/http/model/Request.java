package webserver.http.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static service.SessionService.isSessionValid;
import static webserver.http.HttpParser.*;
import static webserver.http.HttpUtil.HEADER_CONTENT_LENGTH;
import static webserver.http.HttpUtil.HEADER_COOKIE;

public class Request {
    public enum Method {
        GET, POST, PUT, DELETE;

        public static Method getMethodByName(String name) {
            return Arrays.stream(Method.values())
                    .filter(method -> name.equalsIgnoreCase(method.toString()))
                    .collect(Collectors.toList())
                    .get(0);
        }
    }

    private Method method;
    private String version;
    private String targetUri;
    private String path;
    private Map<String, String> queryParameterMap;
    private Map<String, String> headerMap;
    private String sid;
    private String body;
    private Map<String, String> bodyParameterMap;

    public Request(InputStream in) throws IOException {
        parseRequest(in);
    }

    private void parseRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        // Status Line
        String[] tokens = readSingleHTTPLine(br).split(" ");

        Method method = Method.getMethodByName(tokens[0]);
        String targetUri = tokens[1];
        String path = tokens[1].split("\\?")[0];
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
        Map<String, String> bodyParameterMap = new HashMap<>();
        if(method == Method.PUT || method == Method.POST) {
            int contentLength = Integer.parseInt(headerMap.get(HEADER_CONTENT_LENGTH));
            char[] bodyCharacters = new char[contentLength];
            br.read(bodyCharacters);

            body = URLDecoder.decode(String.valueOf(bodyCharacters), StandardCharsets.UTF_8);
            bodyParameterMap = parseBodyParameter(body);
        }

        this.method = method;
        this.version = version;
        this.targetUri = targetUri;
        this.path = path;
        this.queryParameterMap = queryParameterMap;
        this.headerMap = headerMap;
        this.sid = sid;
        this.body = body;
        this.bodyParameterMap = bodyParameterMap;
    }

    public Method getMethod() {
        return method;
    }

    public String getVersion() {
        return version;
    }

    public String getTargetUri() {
        return targetUri;
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getQueryParameterMap() {
        return queryParameterMap;
    }

    public String getQueryParameter(String key) {
        return queryParameterMap.get(key);
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public String getHeader(String key) {
        return headerMap.get(key);
    }

    public String getSid() {
        return sid;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getBodyParameterMap() {
        return bodyParameterMap;
    }

    public String getBodyParameter(String key) {
        return bodyParameterMap.get(key);
    }

    // queryParamter 혹은 bodyParameter에 해당 key가 존재하면 value를 반환합니다.(그렇지 않다면 null 반환)
    public String getParameter(String key) {
        if(queryParameterMap.containsKey(key)) {
            return queryParameterMap.get(key);
        }
        if(bodyParameterMap.containsKey(key)) {
            return bodyParameterMap.get(key);
        }

        return null;
    }
}
