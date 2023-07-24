package common.http;

public class Headers extends KeyValuePair {

    public Headers() {
        super();
    }

    public Headers(String headerString) {
        super(headerString, "\r\n", ":");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Headers that = (Headers) o;

        return this.map.equals(that.map);
    }

    @Override
    public int hashCode() {
        return this.map.hashCode();
    }

}
