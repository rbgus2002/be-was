package utils;

import exception.BadRequestException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static exception.BadRequestException.BAD_REQUEST_MESSAGE;

public class RequestParser {

    public static Map<String, String> parseRequestLine(String requestLine) {
        if (requestLine == null) {
            throw new BadRequestException(BAD_REQUEST_MESSAGE);
        }

        String[] tokens = requestLine.split(" ");
        Map<String, String> requestLineMap = new HashMap<>();
        requestLineMap.put("method", tokens[0]);
        requestLineMap.put("uri", tokens[1]);
        requestLineMap.put("version", tokens[2]);
        return requestLineMap;
    }

    public static Map<String, String> parseRequestHeaders(BufferedReader br) throws IOException {
        String line;
        Map<String, String> requestHeaders = new HashMap<>();

        while((line = br.readLine()) != null && !line.isEmpty()) {
            String[] header = line.split(": ");
            requestHeaders.put(header[0].trim(), header[1].trim());
        }

        return requestHeaders;
    }
}
