package webserver.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import static utils.StringUtils.appendNewLine;

public class Header {
    private final Map<String, String> header = new HashMap<>();
    private final String CONTENT_LENGTH = "Content-Length";


    private Header(BufferedReader br) throws IOException {
        String line;
        while (!isNullOrBlank(line = br.readLine())){
            int colonIndex = line.indexOf(':');
            header.put(line.substring(0, colonIndex), line.substring(colonIndex + 2));
        }
    }

    public static Header from(BufferedReader br) throws IOException {
        return new Header(br);
    }

    private boolean isNullOrBlank(String line) {
        return line == null || line.isBlank();
    }

    public int getContentLength() {
        return Integer.parseInt(header.getOrDefault(CONTENT_LENGTH, "0"));
    }

    public boolean containsContentLength() {
        return header.containsKey(CONTENT_LENGTH);
    }

    public int size(){
        return header.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String key : header.keySet()) {
            sb.append(key + ": ").append(appendNewLine(header.get(key)));
        }
        return sb.toString();
    }
}
