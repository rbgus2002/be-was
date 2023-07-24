package webserver.myframework.session;

public interface Session {
    String SESSION_KEY = "JSESSIONID";

    String getSessionId();
    void setAttribute(String key, Object object);
    Object getAttribute(String key);
}
