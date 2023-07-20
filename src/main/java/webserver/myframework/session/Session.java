package webserver.myframework.session;

public interface Session {
    void setAttribute(String key, Object object);
    Object getAttribute(String key);
}
