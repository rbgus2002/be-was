package util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    private static final Pattern pat = Pattern.compile("([^&=]+)=([^&]*)");


    private StringUtils() {
    }

    public static String getExtension(String path) {
        String[] split = path.split("\\.");
        return split[split.length - 1];
    }

    public static Map<String, String> parseParameters(String query) {
        if (query == null) {
            return Collections.emptyMap();
        }
        Matcher matcher = pat.matcher(query);
        Map<String, String> map = new HashMap<>();
        while (matcher.find()) {
            map.put(matcher.group(1), matcher.group(2));
        }
        return map;
    }
}
