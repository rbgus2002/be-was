package common.wrapper;

public class Cookies extends KeyValuePair {

    public Cookies() {
        super();
    }

    public Cookies(String cookieString) {
        super(cookieString, ";\\s?", "=");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cookies that = (Cookies) o;

        return this.map.equals(that.map);
    }

    @Override
    public int hashCode() {
        return this.map.hashCode();
    }

}
