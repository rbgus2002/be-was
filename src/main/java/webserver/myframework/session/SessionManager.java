package webserver.myframework.session;

public interface SessionManager {
    Session createSession(String sessionId);

    Session findSession(String sessionId);

    void deleteSession(String sessionId);
}
