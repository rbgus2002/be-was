package webserver.http.response.process;

public class HtmlProcessStrategy implements ContentProcessStrategy {
    private static final String ROOT = "/templates/";

    @Override
    public String getRoot() {
        return ROOT;
    }
}
