package webserver.view;

import java.io.File;

public class HtmlViewResolverImpl implements ViewResolver {
    private static final String HTML_PREFIX_URI = "src/main/resources/templates";

    @Override
    public View resolveView(String viewPath) {
        return new HtmlView(new File(HTML_PREFIX_URI + viewPath));
    }
}
