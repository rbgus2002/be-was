package webserver.http;

import webserver.server.WebServer;

import static webserver.http.ResponseHeader.*;
import static webserver.http.StatusLine.*;

public class RequestMessageHeader {
    private static final String KEY_VALUE_PARSER = ": ";
    private static final String SEMI_COLUMN = ";";
    private static final String EQUAL = "=";
    private static final String CHARSET = "charset";
    private static final String UTF_8 = "utf-8";
    private static final String NEW_LINE = "\r\n";

    public String response200Header(int bodyOfLength, String contentType) {
        String header = "";
        header += getStatusHeader(RESPONSE_200)+ NEW_LINE;
        header += getContentTypeHeader(contentType);
        header += CONTENT_LENGTH.getConstant() + KEY_VALUE_PARSER + bodyOfLength + NEW_LINE;
        header += NEW_LINE;

        return header;

    }

    public String response302Header(String redirectUrl) {
        String header = "";
        header += getStatusHeader(RESPONSE_302)+ NEW_LINE;
        header += LOCATION.getConstant() + KEY_VALUE_PARSER + WebServer.HOME_URL + redirectUrl + NEW_LINE;
        header += CONTENT_LENGTH.getConstant() + KEY_VALUE_PARSER + "0" + NEW_LINE;
        header += NEW_LINE;

        return header;
    }

    public String response404Header() {
        return getStatusHeader(RESPONSE_404)+ NEW_LINE;
    }

    private static String getContentTypeHeader(String contentType) {
        return CONTENT_TYPE.getConstant() + KEY_VALUE_PARSER + contentType + SEMI_COLUMN + CHARSET + EQUAL + UTF_8 + NEW_LINE;
    }


}
