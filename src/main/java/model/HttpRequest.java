package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.ServletContainer;

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

    private Map<String, String> headers;
    private Map<String, String> parameters;

    //TODO: ReadLine() 하지마라
    public HttpRequest(InputStream in) throws IOException {
        headers = new HashMap<>();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

        String input = bufferedReader.readLine();
        logger.debug(input);
        String[] startLine = input.split(" ");
        METHOD = HttpMethod.valueOf(startLine[0]);
        URI = startLine[1];
        VERSION = startLine[2];

        //TODO: 일급 컬렉션으로 헤더 별도 저장하기
        while (!(input = bufferedReader.readLine()).equals("")) {
            StringTokenizer tokenizer = new StringTokenizer(input, ":");

            String key = tokenizer.nextToken().trim();
            StringBuilder value = new StringBuilder();
            while (tokenizer.hasMoreTokens()) {
                value.append(tokenizer.nextToken()).append(":");
            }
            value.deleteCharAt(value.length() - 1);
            headers.put(key, value.toString().trim());
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

}
