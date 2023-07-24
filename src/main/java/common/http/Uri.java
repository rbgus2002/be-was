package common.http;

import common.enums.ContentType;

import static common.enums.ContentType.*;
import static common.enums.ContentType.NONE;

public class Uri {
    private final String requestPath;
    private final Queries queries;

    public Uri(String uri) {
        String[] parts = uri.trim().split("\\?");

        requestPath = parts[0].trim();
        queries = (parts.length > 1) ? new Queries(parts[1].trim()) : null;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public Queries getQueries() {
        return queries;
    }

    public ContentType getContentType() {
        if (requestPath.endsWith(".html")) {
            return HTML;
        }
        if (requestPath.endsWith(".css")) {
            return CSS;
        }
        if (requestPath.endsWith(".js")) {
            return JS;
        }
        if (requestPath.endsWith(".ico")) {
            return ICO;
        }
        if (requestPath.endsWith(".png")) {
            return PNG;
        }
        if (requestPath.endsWith(".jpg")) {
            return JPG;
        }
        if (requestPath.endsWith(".eot")) {
            return EOT;
        }
        if (requestPath.endsWith(".svg")) {
            return SVG;
        }
        if (requestPath.endsWith(".ttf")) {
            return TTF;
        }
        if (requestPath.endsWith(".woff")) {
            return WOFF;
        }
        if (requestPath.endsWith(".woff2")) {
            return WOFF2;
        }
        return NONE;
    }

}
