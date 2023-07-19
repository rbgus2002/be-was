package webserver.http.request;

public class RequestTarget {
    private final String value;

    public RequestTarget(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
