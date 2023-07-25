package global.util;

import exception.BadRequestException;
import exception.UnauthorizedException;
import global.constant.Headers;
import model.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {
    private static final String SEMI_COLUMN_SEPARATOR = ";";
    private static final String EMPTY_SPACE_SEPARATOR = " ";
    private static final String EQUAL_SEPARATOR = "=";

    private static final Map<String, User> sessionStorage = new ConcurrentHashMap<>();

    public SessionUtil() {
    }

    public User getSession(String header) {
        return sessionStorage.get(parseSessionId(header));
    }

    public String getSessionByUser(User user) {
        for (String key : sessionStorage.keySet()) {
            User value = sessionStorage.get(key);
            if (value.equals(user)) {
                return key;
            }
        }
        throw new UnauthorizedException();
    }

    public Map<String, User> getAllSessions() {
        return sessionStorage;
    }

    public void setSession(String header, User user) {
        sessionStorage.put(parseSessionId(header), user);
    }

    public void clearSession() {
        sessionStorage.clear();
    }


    private String parseSessionId(String header) {
        String[] splitResults = header.split(EMPTY_SPACE_SEPARATOR);
        for (String result : splitResults) {
            if (result.contains(Headers.SESSION_ID.getKey())) {
                return result.split(EQUAL_SEPARATOR)[1].split(SEMI_COLUMN_SEPARATOR)[0];
            }
        }
        throw new BadRequestException();
    }
}
