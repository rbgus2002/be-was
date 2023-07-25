package common.wrapper;

public class Queries extends KeyValuePair {
    public Queries() {
        super();
    }

    public Queries(String queryString) {
        super(queryString, "&", "=");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Queries that = (Queries) o;

        return this.map.equals(that.map);
    }

    @Override
    public int hashCode() {
        return this.map.hashCode();
    }

}
