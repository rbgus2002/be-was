package support.utils;

public class StringUtils {

    public static final String NEWLINE = System.getProperty("line.separator");

    private StringUtils() {
    }

    public static String appendNewLine(final String s) {
        return s + NEWLINE;
    }
}
