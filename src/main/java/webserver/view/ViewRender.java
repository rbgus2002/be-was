package webserver.view;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ViewRender {
    private static final Pattern REND_PATTERN = Pattern.compile("rend:\\{(.*?)\\}");

    public static String createPage(ViewData viewData) throws IOException {
        String body = Files.readString(Paths.get(viewData.getPath()));
        return render(body, viewData.getMatchedData());
    }

    public static String render(String body, Map<String, String> matchedData) {
        Matcher matcher = REND_PATTERN.matcher(body);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            String result = matcher.group(1);
            String[] tokens = result.split(":");
            if (matchedData.containsKey(tokens[0]) && matchedData.get(tokens[0]) != null) {
                matcher.appendReplacement(sb, matchedData.get(tokens[0]));
                continue;
            }
            matcher.appendReplacement(sb, tokens[1]);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
