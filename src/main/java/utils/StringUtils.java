package utils;

public class StringUtils {
    public static final String NEW_LINE = System.getProperty("line.separator");

    public static boolean isEmpty(String str) {
        return str.equals("") || str.equals(" ");
    }
}
