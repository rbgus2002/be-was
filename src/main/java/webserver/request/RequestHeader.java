package webserver.request;

import utils.StringUtils;

import java.util.Arrays;
import java.util.HashMap;

public class RequestHeader {

    private final HashMap<String, String> headers;

    private RequestHeader(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public static RequestHeader of(String string) {
        String[] tokens = string.split(StringUtils.NEWLINE);

        HashMap<String, String> headers = new HashMap<>();

        Arrays.stream(tokens)
                .map(token -> token.split(":"))
                .forEach(header -> headers.put(header[0].trim(), header[1].trim()));

        return new RequestHeader(headers);
    }
}
