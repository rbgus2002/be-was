package model;

import java.util.StringTokenizer;

public enum ContentType {

    TEXT_PLAIN("text", "plain", "charset=UTF-8"),
    TEXT_HTML("text", "html", "charset=UTF-8"),
    TEXT_CSS("text", "css"),
    TEXT_JAVASCRIPT("text", "javascript"),
    APPLICATION_JSON("application", "json"),
    IMAGE_JPEG("image", "jpeg"),
    IMAGE_X_ICON("image", "x-icon"),
    IMAGE_PNG("image", "png"),
    APPLICATION_URLENCODED("application", "x-www-form-urlencoded"),
    FONT_WOFF("font", "woff"),
    FONT_TTF("font", "ttf"),
    MULTIPART_FORMDATA("multipart", "form-data");

    private String name;
    private String value;
    private String parameter;

    ContentType(String name, String value) {
        this.name = name;
        this.value = value;
    }

    ContentType(String name, String value, String parameter) {
        this.name = name;
        this.value = value;
        this.parameter = parameter;
    }

    public String getContentType() {
        StringBuilder stringBuilder = getType();

        if(parameter != null) {
            stringBuilder.append(";").append(parameter);
        }

        return stringBuilder.toString();
    }

    private StringBuilder getType() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(name).append("/").append(value);

        return stringBuilder;
    }

    public static ContentType of(String requestedType) {
        StringTokenizer stringTokenizer = new StringTokenizer(requestedType, "/:");

        if(stringTokenizer.countTokens() < 2) {
            return null;
        }

        String requestedName = stringTokenizer.nextToken();
        String requestedValue = stringTokenizer.nextToken();

        for (ContentType contentType : ContentType.values()) {
            if(contentType.name.equals(requestedName) && contentType.value.equals(requestedValue)) {
                return contentType;
            }
        }
        return null;
    }
}
