package controller;

import webserver.http.HttpUtil;
import webserver.http.model.Response;

import java.util.HashMap;
import java.util.Map;

import static webserver.http.HttpUtil.*;
import static webserver.http.HttpUtil.HEADER_REDIRECT_LOCATION;

public abstract class Controller {
    protected static Response generate200Response(HttpUtil.MIME mime, byte[] body) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put(HEADER_CONTENT_TYPE, mime.getMime() + HEADER_CHARSET);
        headerMap.put(HEADER_CONTENT_LENGTH, String.valueOf(body.length));

        return new Response(STATUS.OK, headerMap, body);
    }

    protected static Response generate303Response(String redirectUrl) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put(HEADER_REDIRECT_LOCATION, redirectUrl);
        return new Response(STATUS.SEE_OTHER, headerMap, null);
    }
}
