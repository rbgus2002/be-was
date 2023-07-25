package common.http;

import java.util.Objects;

public class Cookie {

    private final String name;
    private final String value;
    private final String path;

    private Cookie(String name, String value, String path) {
        this.name = name;
        this.value = value;
        this.path = path;
    }

    public static Cookie of(String name, String value) {
        return new Cookie(name, value, null);
    }

    public static Cookie of(String name, String value, String path) {
        return new Cookie(name, value, path);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cookie cookie = (Cookie) o;

        if (!Objects.equals(name, cookie.name)) return false;
        if (!Objects.equals(value, cookie.value)) return false;
        return Objects.equals(path, cookie.path);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder cookieBuilder = new StringBuilder();
        cookieBuilder.append(name).append("=").append(value).append("; ");

        if (path != null) {
            cookieBuilder.append("Path=").append(path).append("; ");
        }

        return cookieBuilder.toString();
    }

}
