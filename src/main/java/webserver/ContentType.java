package webserver;

public enum ContentType {

    HTML("text", "html");

    private final String type;
    private final String subType;

    ContentType(String type, String subType) {
        this.type = type;
        this.subType = subType;
    }

    public String getDescription() {
        return type + "/" + subType;
    }
}
