package utils;

import db.Database;
import model.Session;
import webserver.request.HttpRequest;

import java.util.Arrays;
import java.util.Optional;

public abstract class LoginUtils {

    public static Session getLoginSession(HttpRequest request) {
        String cookieHeader = request.getHeaderValue("Cookie");
        if (cookieHeader != null) {
            String[] headerValue = cookieHeader.split(" ");
            Optional<String> sessionIdOptional = Arrays.stream(headerValue)
                    .filter(s -> s.startsWith("sid"))
                    .findAny();
            if (sessionIdOptional.isPresent()) {
                String sessionIdQuery = sessionIdOptional.get();
                return Database.findSessionById(sessionIdQuery.substring(4));
            }
        }
        return null;
    }
}
