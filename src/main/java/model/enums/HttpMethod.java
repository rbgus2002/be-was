package model.enums;

public enum HttpMethod {
    GET,
    POST,
    PUT,
    PATCH,
    DELETE
    ;

    public static HttpMethod getValueOf(String name) {
        return Enum.valueOf(HttpMethod.class, name);
    }
}
