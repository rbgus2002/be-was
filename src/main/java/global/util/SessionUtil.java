package global.util;

import exception.BadRequestException;
import exception.UnauthorizedException;
import global.constant.Headers;
import model.Session;
import model.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SessionUtil {
    private static final String SEMI_COLUMN_SEPARATOR = ";";
    private static final String EMPTY_SPACE_SEPARATOR = " ";
    private static final String EQUAL_SEPARATOR = "=";

    private static final long SESSION_TIMEOUT_MS = 30 * 60 * 1000;

    private static final Map<String, Session> sessionStorage = new ConcurrentHashMap<>();

    public SessionUtil() {
    }

    public String createSession() {
        String sessionId = UUID.randomUUID().toString();
        sessionStorage.put(sessionId, new Session());
        Session session = sessionStorage.get(sessionId);

        setTimer(session, sessionId);

        return sessionId;
    }

    public Session getSession(String header) {
        return sessionStorage.get(parseSessionId(header));
    }

    public String getSessionByUser(Session session) {
        for (String key : sessionStorage.keySet()) {
            Session value = sessionStorage.get(key);
            if (value.equals(session)) {
                return key;
            }
        }
        throw new UnauthorizedException();
    }

    public Map<String, Session> getAllSessions() {
        return sessionStorage;
    }

    public void setSession(String sid, Session session) {
        sessionStorage.put(sid, session);
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


    private void setTimer(Session session, String sessionId) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            if (session.getLastAccessedTime() + SESSION_TIMEOUT_MS <= System.currentTimeMillis()) {
                sessionStorage.remove(sessionId);
                scheduler.shutdown();
            }
        }, SESSION_TIMEOUT_MS, SESSION_TIMEOUT_MS, TimeUnit.MILLISECONDS);
    }
}
