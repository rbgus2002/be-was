package webserver.http.response;

public class Cookie {
    private static final int DEFAULT_MAX_AGE = 3600 * 30;
    private final String name;
    private final String value;
    private int maxAge = DEFAULT_MAX_AGE;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }
}
