package common.http;

import common.enums.ContentType;
import common.wrapper.Queries;

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
        if (!requestPath.contains(".")) {
            return NONE;
        }

        String extension = extractExtensionFromRequestPath();

        try {
            return ContentType.valueOf(extension.toUpperCase());
        } catch (IllegalArgumentException e) {
            return NONE;
        }
    }

    private String extractExtensionFromRequestPath() {
        for (int i = requestPath.length() - 1; i >= 0; i--) {
            if (requestPath.charAt(i) == '.') {
                return requestPath.substring(i + 1);
            }
        }
        return "";
    }

}
