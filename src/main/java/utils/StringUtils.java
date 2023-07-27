package utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class StringUtils {
    public static final String CRLF = "\r\n";
    public static final String QUESTION_MARK = "\\?";
    public static final String SPACE = " ";
    public static final String EQUAL = "=";
    public static final String COLON = ":";
    public static final String AMPERSAND = "&";
    public static final String DOT = "\\.";

    private StringUtils() {
    }

    public static String appendNewLine(String str) {
        return str + CRLF;
    }

    public static String decode(String str){
        return URLDecoder.decode(str, StandardCharsets.UTF_8);
    }
}
