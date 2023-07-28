package http.request;

public enum RequestMethod {
    GET,
    POST,
    PUT,
    PATCH,
    DELETE;

    public static RequestMethod of(String method) {
        return RequestMethod.valueOf(method);
    }
}
