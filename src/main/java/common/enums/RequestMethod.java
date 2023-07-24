package common.enums;

public enum RequestMethod {
    GET, POST, PUT, DELETE;

    public static RequestMethod of(String requestMethod) {
        return RequestMethod.valueOf(requestMethod);
    }

}
