package common.enums;

public enum Method {
    GET, POST, PUT, DELETE;

    public static boolean isGetMethod(Method method) {
        return method.equals(GET);
    }
}