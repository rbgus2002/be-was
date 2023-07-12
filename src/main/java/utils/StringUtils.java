package utils;

public abstract class StringUtils {

    public static final String NEW_LINE = System.getProperty("line.separator");

    public static String appendNewLine(final String... messages) {
        return String.join(NEW_LINE, messages);
    }
}
