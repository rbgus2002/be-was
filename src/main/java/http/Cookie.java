package http;

public class Cookie {
    private final String name;
    private final String value;
    private final String path;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
        this.path = "/";
    }

    public static Cookie from(String name, String value){
        return new Cookie(name, value);
    }



    @Override
    public String toString() {
        return String.format("%s=%s; Path=%s", name, value, path);
    }
}
