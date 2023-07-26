package webserver.container;

import java.util.Objects;

public class EndPoint {
    String path;
    String method;

    public EndPoint(String path, String method) {
        this.path = path;
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EndPoint endPoint = (EndPoint) o;
        return Objects.equals(path, endPoint.path) && Objects.equals(method, endPoint.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, method);
    }
}
