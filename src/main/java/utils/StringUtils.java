package utils;

public class StringUtils {
    public static final String NEW_LINE = System.getProperty("line.separator");

    public static String appendNewLine(String message) {
        return message + NEW_LINE;
    }

}
