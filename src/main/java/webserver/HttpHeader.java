package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import static utils.StringUtils.*;

public class HttpHeader {
    private HttpRequestLine uri;
    private Map<String, String> header = new HashMap<>();

    private HttpHeader(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = br.readLine();
        this.uri = HttpRequestLine.from(line);

        while (!isNullOrBlank(line)){
            line = br.readLine();
            if(isNullOrBlank(line)){
                break;
            }
            StringTokenizer st = new StringTokenizer(line, ": ");
            header.put(st.nextToken(), st.nextToken());
        }
    }

    public static HttpHeader from(InputStream in) throws IOException {
        return new HttpHeader(in);
    }

    private static boolean isNullOrBlank(String line) {
        return line == null || line.isBlank();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[URI] ").append(appendNewLine(uri.toString()));
        for(String key : header.keySet()){
            sb.append(key + ": ").append(appendNewLine(header.get(key)));
        }
        return sb.toString();
    }

    public String getMethod() {
        return uri.getMethod();
    }

    public String getUri(){
        return uri.getUri();
    }
}