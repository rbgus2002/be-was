package webserver.myframework.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    private StringUtils() {
    }

    public static List<String> splitStringByRegex(String string, String regex) {
        List<String> result = new ArrayList<>();
        Matcher matcher = Pattern.compile(regex).matcher(string);
        int lastIndex = 0;

        while (matcher.find()) {
            String matched = matcher.group();
            int start = matcher.start();
            if (start != lastIndex) {
                result.add(string.substring(lastIndex, start));
            }
            result.add(matched);
            lastIndex = matcher.end();
        }

        if (lastIndex < string.length()) {
            result.add(string.substring(lastIndex));
        }

        return result;
    }
}
