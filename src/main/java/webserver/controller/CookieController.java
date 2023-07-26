package webserver.controller;

import webserver.response.HttpResponseMessage;
import webserver.response.HttpStatus;
import webserver.session.SessionStorage;

import java.util.Map;
import java.util.UUID;

public class CookieController {
    public static void createCookie(HttpResponseMessage response, String value) {
        String sessionId = UUID.randomUUID().toString();
        SessionStorage.setSession(sessionId, value);
        response.setHeader("Set-Cookie", "sid=" + sessionId + "; Expires=Thu, 01 Jan 2070 00:00:00 GMT; path=/");
    }

    public static void deleteCookie(Map<String, String> cookies, HttpResponseMessage response) {
        String sessionId = cookies.get("sid");
        response.setStatusLine(HttpStatus.OK);
        response.setHeader("Set-Cookie", "sid=" + sessionId + "; Expires=Thu, 01 Jan 2000 00:00:00 GMT; path=/");
    }
}
