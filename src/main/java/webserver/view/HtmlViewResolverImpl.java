package webserver.view;

import java.io.File;

public class HtmlViewResolverImpl implements ViewResolver {
    private static final String HTML_PREFIX = "src/main/resources/templates";
    private static final String HTML_SUFFIX = ".html";
    private static final String DEFAULT_VIEW_PATH = "/errors/404.html";

    @Override
    public View resolve(String viewUri) {
        File html = new File(getViewPath(viewUri));
        return createView(html);
    }

    private static View createView(File html) {
        if (html.exists() && html.canRead()) {
            return new HtmlView(html);
        }
        return new HtmlView(new File(HTML_PREFIX + DEFAULT_VIEW_PATH));
    }

    private static String getViewPath(String viewUri) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HTML_PREFIX)
                .append(viewUri);
        if (!viewUri.endsWith(HTML_SUFFIX)) {
            stringBuilder.append(HTML_SUFFIX);
        }
        return stringBuilder.toString();
    }
}
