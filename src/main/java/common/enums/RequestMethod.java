package common.enums;

public enum RequestMethod {
    GET, POST, PUT, DELETE;

    public static boolean isGetMethod(RequestMethod requestMethod) {
        return requestMethod.equals(GET);
    }
}
