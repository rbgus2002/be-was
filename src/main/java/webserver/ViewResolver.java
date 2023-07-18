package webserver;

import common.enums.ContentType;
import view.View;

import static webserver.ServerConfig.STATIC_PATH;
import static webserver.ServerConfig.TEMPLATE_PATH;

public class ViewResolver {
    public static View resolve(ContentType contentType, String viewName) {
        if (viewName == null) {
            return new View(null);
        }
        if (contentType.isStaticContent()) {
            return new View(STATIC_PATH + viewName);
        }
        if (contentType.isHtmlContent()) {
            return new View(TEMPLATE_PATH + viewName);
        }
        return new View(TEMPLATE_PATH + "/error.html");
    }
}