package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Parser {

    public static Map<String, String> parseRequestLine(String requestLine) {
        String[] tokens = requestLine.split(" ");
        Map<String, String> requestLineMap = new HashMap<>();
        requestLineMap.put("method", tokens[0]);
        requestLineMap.put("uri", tokens[1]);
        requestLineMap.put("version", tokens[2]);
        return requestLineMap;
    }

    public static Map<String, String> parseHeaders(BufferedReader br) throws IOException {
        String line;
        Map<String, String> requestHeaders = new HashMap<>();

        while((line = br.readLine()) != null && !line.isEmpty()) {
            String[] header = line.split(":");
            requestHeaders.put(header[0], header[1]);
        }

        return requestHeaders;
    }
}
