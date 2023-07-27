package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.BodyParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class HttpRequest {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private final HttpMethod METHOD;

    private final String URI;

    private final String VERSION;

    private ContentType contentType;

    //TODO: 일급 컬렉션 사용하기
    private Map<String, String> headers;
    private Map<String, String> parameters;

    private Object body;

    public HttpRequest(InputStream in) throws IOException {
        headers = new HashMap<>();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

        String input = bufferedReader.readLine();
        logger.debug(input);
        String[] startLine = input.split(" ");
        METHOD = HttpMethod.valueOf(startLine[0]);
        URI = startLine[1];
        VERSION = startLine[2];


        //헤더 저장
        //TODO: 일급 컬렉션으로 헤더 별도 저장하기
        while (!(input = bufferedReader.readLine()).isBlank()) {
            StringTokenizer tokenizer = new StringTokenizer(input, ":");

            String key = tokenizer.nextToken().trim();
            StringBuilder value = new StringBuilder();
            while (tokenizer.hasMoreTokens()) {
                value.append(tokenizer.nextToken()).append(":");
            }
            value.deleteCharAt(value.length() - 1);
            headers.put(key, value.toString().trim());
        }


        if(headers.containsKey("Content-Type")) {
            contentType = ContentType.of(headers.get("Content-Type"));

            //바디 저장
        }
        if(contentType != null) {
            int contentLength = Integer.parseInt(headers.get("Content-Length"));
            char[] inputBody = new char[contentLength];
            bufferedReader.read(inputBody);

            try {
                body = BodyParser.parseBody(String.valueOf(inputBody), contentType);
            } catch (RuntimeException e) {
                body = String.valueOf(inputBody);
            }
        }

        if(URI.indexOf('?') != -1) {
            parameters = new HashMap<>();

            int index = URI.indexOf('?');
            String queryParameters = URI.substring(index + 1);
            String[] queries = queryParameters.split("&");

            for(String data : queries) {
                StringTokenizer tokenizer = new StringTokenizer(data, "=");

                String key = tokenizer.nextToken().toString();
                String value = "";
                if(tokenizer.hasMoreTokens()) {
                    value = tokenizer.nextToken().toString();
                }

                parameters.put(key, value);
            }
        }
    }

    public String getRequestURL() {
        String domain = "";
        try {
            if((domain = headers.get("Host")) == null) {
                domain = String.valueOf(InetAddress.getLocalHost());
            }
        } catch (UnknownHostException exception) {
            logger.error(exception.getMessage());
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(domain).append(URI);

        return stringBuilder.toString();
    }

    public String getRequestURI() {
        if(URI.indexOf('?') != -1) {
            return URI.substring(0, URI.indexOf('?'));
        }

        return URI;
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public HttpMethod getMethod() {
        return METHOD;
    }

    public String getQueryString() {
        return URI.substring(URI.indexOf('?')+1);
    }

    public String getVersion() {
        return VERSION;
    }

    public String getParameter(String name) {
        return parameters.get(name);
    }

    public String[] getParameterValues() {
        return parameters.keySet().toArray(new String[0]);
    }

    public String getContentType() {
        return headers.get("Content-Type");
    }

    public Object getBody() {
        return body;
    }

}
