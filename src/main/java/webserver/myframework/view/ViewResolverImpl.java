package webserver.myframework.view;

import webserver.myframework.bean.annotation.Component;

import java.io.File;

@Component
public class ViewResolverImpl implements ViewResolver {
    private static final String RESOURCE_PREFIX = "src/main/resources";
    private static final String DYNAMIC_VIEW = "/templates";
    private static final String STATIC_VIEW = "/static";
    private static final String DEFAULT_VIEW_PATH = "/errors/404.html";

    @Override
    public View resolve(String viewUri) {
        File dynamicFile = new File(getViewPath(viewUri, false));
        if(dynamicFile.exists() && dynamicFile.isFile() && dynamicFile.canRead()) {
            return new StaticView(dynamicFile);
        }

        File staticFile = new File(getViewPath(viewUri, true));
        if(staticFile.exists() && staticFile.isFile() && staticFile.canRead()) {
            return new StaticView(staticFile);
        }

        return new StaticView(new File(RESOURCE_PREFIX + DYNAMIC_VIEW + DEFAULT_VIEW_PATH));
    }

    private static String getViewPath(String viewUri, boolean isStatic) {
        return RESOURCE_PREFIX +
               (isStatic ? STATIC_VIEW : DYNAMIC_VIEW) +
               (viewUri.startsWith("/") ? "" : "/") +
               viewUri;
    }
}
