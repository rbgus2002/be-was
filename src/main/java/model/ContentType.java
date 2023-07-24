package model;

public final class ContentType {

    private final String name;
    private final String value;
    private final String parameter;

    private ContentType(String name, String value, String parameter) {
        this.name = name;
        this.value = value;
        this.parameter = parameter;
    }

    private ContentType(String name, String value) {
        this.name = name;
        this.value = value;
        parameter = null;
    }

    public static final ContentType TEXT_PLAIN = new ContentType("text", "plain", "charset=UTF-8");
    public static final ContentType TEXT_HTML = new ContentType("text", "html", "charset=UTF-8");
    public static final ContentType TEXT_CSS = new ContentType("text", "css");
    public static final ContentType TEXT_JAVASCRIPT = new ContentType("text", "javascript");
    public static final ContentType APPLICATION_JSON = new ContentType("application", "json");
    public static final ContentType IMAGE_JPEG = new ContentType("image", "jpeg");
    public static final ContentType IMAGE_X_ICON = new ContentType("image", "x-icon");
    public static final ContentType IMAGE_PNG = new ContentType("image", "png");
    public static final ContentType APPLICATION_URLENCODED = new ContentType("application", "x-www-form-urlencoded");
    public static final ContentType FONT_WOFF = new ContentType("font", "woff");
    public static final ContentType FONT_TTF = new ContentType("font", "ttf");

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
}
