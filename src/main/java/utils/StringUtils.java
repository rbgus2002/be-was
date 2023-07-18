package utils;

public class StringUtils {
    public static final String NEWLINE = "\r\n";

    private StringUtils() {
    }

    public static String appendNewLine(String str) {
        return str + NEWLINE;
    }
}
