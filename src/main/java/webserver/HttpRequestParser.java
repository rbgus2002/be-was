package webserver;

import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpVersion;
import webserver.http.URL;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequestParser {
    public HttpRequest parseHttpMessage(String httpMessage) {
        String[] messageLines = httpMessage.split("\n");
        int requestLineIndex = 0;
        int blankLineIndex = findBlankLine(messageLines);

        String[] requestLineTokens = messageLines[requestLineIndex].split(" ");
        HttpMethod method = HttpMethod.from(requestLineTokens[0]);
        URL url = URL.from(requestLineTokens[1]);
        HttpVersion httpVersion = HttpVersion.from(requestLineTokens[2]);
        Map<String, List<String>> metaData = getMetaData(messageLines, requestLineIndex, blankLineIndex);

        String body = null;
        if (hasMessageBody(messageLines, blankLineIndex)) {
            body = getBody(messageLines, blankLineIndex);
        }
        return new HttpRequest(method, url, httpVersion, metaData, body);
    }

    private String getBody(String[] messageLines, int blankLineIndex) {
        return Arrays.stream(messageLines, blankLineIndex + 1, messageLines.length)
                .collect(Collectors.joining());
    }

    private static boolean hasMessageBody(String[] messageLines, int blankLineIndex) {
        return blankLineIndex + 1 < messageLines.length;
    }

    private Map<String, List<String>> getMetaData(String[] messageLines, int requestLineIndex, int blankLineIndex) {
        Map<String, List<String>> metaData = new HashMap<>();
        for (int lineIdx = requestLineIndex + 1; lineIdx < blankLineIndex; lineIdx++) {
            String headerLine = messageLines[lineIdx].replaceAll("[\\s\r\n]", "");
            String[] tokens = headerLine.split(":");
            String header = tokens[0];
            String[] values = tokens[1].split(",");
            metaData.put(header, List.of(values));
        }
        return metaData;
    }

    private int findBlankLine(String[] messageLines) {
        for (int lineIdx = 0; lineIdx < messageLines.length; lineIdx++) {
            if (messageLines[lineIdx].equals("\r")) {
                return lineIdx;
            }
        }
        return -1;
    }
}
