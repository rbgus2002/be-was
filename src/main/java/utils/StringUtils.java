package utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class StringUtils {
    public static final String NEW_LINE = System.getProperty("line.separator");

    public static String appendNewLine(String message) {
        return message + NEW_LINE;
    }

    public static String decodeBody(String encoded) {
        return URLDecoder.decode(encoded, StandardCharsets.UTF_8);
    }

}
