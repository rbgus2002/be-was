package webserver.myframework.view;

import webserver.myframework.bean.annotation.Component;

import java.io.File;

@Component
public class StaticViewResolverImpl implements ViewResolver {
    private static final String HTML_PREFIX = "src/main/resources";
    private static final String DYNAMIC_VIEW = "/templates";
    private static final String STATIC_VIEW = "/static";
    private static final String HTML_SUFFIX = ".html";
    private static final String DEFAULT_VIEW_PATH = "/errors/404.html";

    @Override
    public View resolve(String viewUri) {
        File dynamicFile = new File(getViewPath(viewUri, false));
        if(dynamicFile.exists() && dynamicFile.canRead()) {
            return new StaticView(dynamicFile);
        }

        File staticFile = new File(getViewPath(viewUri, true));
        if(staticFile.exists() && staticFile.canRead()) {
            return new StaticView(staticFile);
        }

        return new StaticView(new File(HTML_PREFIX + DYNAMIC_VIEW +  DEFAULT_VIEW_PATH));
    }

    private static String getViewPath(String viewUri, boolean isStatic) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HTML_PREFIX)
                .append(isStatic ? STATIC_VIEW : DYNAMIC_VIEW)
                .append(viewUri);
        if (!hasExtension(viewUri)) {
            stringBuilder.append(HTML_SUFFIX);
        }
        return stringBuilder.toString();
    }

    private static boolean hasExtension(String filename) {
        if (filename == null) {
            return false;
        }
        return filename.lastIndexOf('.') >
               Math.max(filename.lastIndexOf('/'), filename.lastIndexOf('\\'));
    }
}
