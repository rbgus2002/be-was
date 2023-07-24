package model;

public enum HttpMethod {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    PATCH("PATCH"),
    OPTIONS("OPTIONS"),
    HEAD("HEAD"),
    TRACE("TRACE"),
    CONNECT("CONNECT");



    private final String name;
    private static final HttpMethod[] values = new HttpMethod[] {GET, PATCH, PUT, POST, DELETE, OPTIONS, HEAD, TRACE, CONNECT};

    private HttpMethod(String name) {
        this.name = name;
    }

//    public static HttpMethod valueOf(String name) {
//        for(HttpMethod method : values) {
//            if(method.name.equals(name)) {
//                return method;
//            }
//        }
//        return null;
//    }

    public String getName() {
        return this.name;
    }

}
