package webserver.http.response;

public class TemplateProcessStrategy implements ContentProcessStrategy {
    private static final String ROOT = "/templates/";

    @Override
    public String getRoot() {
        return ROOT;
    }
}
