package model.enums;

public enum Method {
    GET,
    POST,
    PUT,
    PATCH,
    DELETE
    ;

    public static Method getValueOf(String name) {
        return Enum.valueOf(Method.class, name);
    }
}
