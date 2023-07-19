package webserver.request;

public class Query extends KeyValue {

    public Query() {
        super(true);
    }

    public Query(String queryString) {
        super(queryString, true);
    }

}
