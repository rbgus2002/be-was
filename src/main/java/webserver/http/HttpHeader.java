package webserver.http;

import webserver.server.WebServer;

public class HttpHeader {

    private static final String LOCATION = "Location";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String HTTP_VERSION = "HTTP/1.1 ";
    private static final String CHARSET = "charset";
    private static final String UTF_8 = "UTF-8";

    private static final int RESPONSE_200 = 200;
    private static final int RESPONSE_302 = 302;
    private static final int RESPONSE_404 = 404;

    private static final String NEW_LINE = "\r\n";

    public static String response200Header(int bodyOfLength, String contentType) {
        String header = "";
        header += getFirstHeader(RESPONSE_200);
        header += "Content-Type: " + contentType + ";charset=utf-8\r\n";
        header += "Content-Length: " + bodyOfLength + "\r\n";
        header += "\r\n";

        return header;

    }

    public static String response302Header(String redirectUrl, String contentType) {
        String header = "";
        header += getFirstHeader(RESPONSE_302);
        header += LOCATION + ": " + WebServer.HOME_URL + redirectUrl + NEW_LINE;
        header += CONTENT_TYPE + ": " + contentType + ";" + CHARSET + "=" + UTF_8 + NEW_LINE;
        header += CONTENT_LENGTH + ": 0" + NEW_LINE;
        header += NEW_LINE;

        return header;
    }

    public static String response404Header() {
        return getFirstHeader(RESPONSE_404);
    }

    private static String getFirstHeader(int code) {
        return HTTP_VERSION + code + NEW_LINE;
    }



}
