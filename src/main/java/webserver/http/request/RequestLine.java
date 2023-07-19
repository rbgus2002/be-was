package webserver.http.request;

import static webserver.http.Http.SPACING_DELIMITER;

import webserver.http.Http.Method;
import webserver.http.Http.Version;

public class RequestLine {
    private final Method method;
    private final RequestTarget target;
    private final Version version;

    public RequestLine(final Method method, final RequestTarget target, final Version version) {
        this.method = method;
        this.target = target;
        this.version = version;
    }

    public Method getMethod() {
        return method;
    }

    public RequestTarget getTarget() {
        return target;
    }

    public Version getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(method.name())
                .append(SPACING_DELIMITER)
                .append(target)
                .append(SPACING_DELIMITER)
                .append(version.getValue())
                .toString();
    }
}
