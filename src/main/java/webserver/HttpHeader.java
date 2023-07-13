package webserver;

import utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import static utils.StringUtils.*;

public class HttpHeader {
    private String uri;
    private Map<String, String> header = new HashMap<>();

    private HttpHeader(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = br.readLine();
        this.uri = line;

        while (!line.equals("")){
            line = br.readLine();
            if(line == null || line.equals("")){
                break;
            }
            StringTokenizer st = new StringTokenizer(line, ": ");
            header.put(st.nextToken(), st.nextToken());
        }
    }

    public static HttpHeader from(InputStream in) throws IOException {
        return new HttpHeader(in);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[URI] ").append(appendNewLine(uri));
        for(String key : header.keySet()){
            sb.append(key + ": ").append(appendNewLine(header.get(key)));
        }

        return sb.toString();
    }
}
