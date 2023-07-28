package utils;

public abstract class StringUtils {
    
    public static final String CRLF = "\r\n";

    public static String appendNewLine(final String... messages) {
        return String.join(CRLF, messages);
    }

}
