package webserver;

import static webserver.ServerConfig.STATIC_PATH;
import static webserver.ServerConfig.TEMPLATE_PATH;

public class PathResolver {
    public static String resolve(String viewName) {
        if (isStaticFile(viewName)) {
            return STATIC_PATH + viewName;
        }
        if (viewName.endsWith(".html")) {
            return TEMPLATE_PATH + viewName;
        }
        return TEMPLATE_PATH + "/error.html";
    }

    private static boolean isStaticFile(String viewName) {
        return viewName.startsWith("/css") || viewName.startsWith("/font") ||
                viewName.startsWith("/images") || viewName.startsWith("/js") ||
                viewName.endsWith(".ico");
    }
}