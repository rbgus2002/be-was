package webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import static utils.StringUtils.NEW_LINE;

public class HttpRequestParser {

    public static HttpRequest parseRequest(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder stringBuilder = new StringBuilder();
        char[] buffer = new char[1024]; // 읽을 버퍼 사이즈는 성능에 따라 정하기
        int bytesRead;
        while (bufferedReader.ready()) {
            bytesRead = bufferedReader.read(buffer);
            stringBuilder.append(buffer, 0, bytesRead);
        }

        String readAll = stringBuilder.toString();

        int crlfIndex = readAll.indexOf(NEW_LINE + NEW_LINE);
        String header = readAll.substring(0, crlfIndex);
        String body = "";
        if (crlfIndex > 0) {
            /* \r\n 바이트 이후 body 가 시작되므로 */
            body = readAll.substring(crlfIndex + 4);
        }

        String[] lines = header.split(NEW_LINE);
        String[] statusLine = lines[0].split(" ");
        if (statusLine.length != 3) {
            throw new IllegalArgumentException("잘못된 Status line 입니다.");
        }

        String method = statusLine[0];
        String url = statusLine[1];
        String version = statusLine[2];

        Map<String, String> headers = new HashMap<>();
        IntStream.range(1, lines.length).forEach(i -> {
            String[] tokens = lines[i].split(": ?");
            headers.put(tokens[0], tokens[1]);
        });

        return new HttpRequest(method, url, version, headers, body);
    }
}
