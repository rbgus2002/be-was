package webserver.http;

import webserver.server.WebServer;

import static webserver.http.HttpConstant.*;

public class HttpHeader {

    public static String response200Header(int bodyOfLength, String contentType) {
        String header = "";
        header += getFirstHeader(RESPONSE_200.getConstant());
        header += getContentTypeHeader(contentType);
        header += CONTENT_LENGTH.getConstant() + ": " + bodyOfLength + NEW_LINE.getConstant();
        header += NEW_LINE.getConstant();

        return header;

    }


    public static String response302Header(String redirectUrl) {
        String header = "";
        header += getFirstHeader(RESPONSE_302.getConstant());
        header += LOCATION.getConstant() + ": " + WebServer.HOME_URL + redirectUrl + NEW_LINE.getConstant();
        header += CONTENT_LENGTH.getConstant() + ": 0" + NEW_LINE.getConstant();
        header += NEW_LINE.getConstant();

        return header;
    }

    public static String response404Header() {
        return getFirstHeader(RESPONSE_404.getConstant());
    }

    private static String getFirstHeader(String code) {
        return HTTP_VERSION.getConstant() + code + NEW_LINE.getConstant();
    }

    private static String getContentTypeHeader(String contentType) {
        return CONTENT_TYPE.getConstant() + ": " + contentType + ";" + CHARSET.getConstant() + "=" + UTF_8.getConstant() + NEW_LINE.getConstant();
    }


}
