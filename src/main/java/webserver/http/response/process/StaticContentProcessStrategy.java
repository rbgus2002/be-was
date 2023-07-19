package webserver.http.response.process;

public class StaticContentProcessStrategy implements ContentProcessStrategy {
    private static final String ROOT = "/static/";

    @Override
    public String getRoot() {
        return ROOT;
    }
}
