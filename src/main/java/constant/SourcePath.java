package constant;

public class SourcePath {
    public static final String BASIC_INDEX_PATH = "/index.html";
    public static final String USER_FORM_PATH = "/user/form.html";
    private static final String RESOURCE_RELATIVE_PATH = "src/main/resources/templates";

    public static String getRelativePath(String path) {
        return RESOURCE_RELATIVE_PATH + path;
    }
}
