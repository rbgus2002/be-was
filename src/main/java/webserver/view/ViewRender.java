package webserver.view;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static utils.StringUtils.NEW_LINE;


public class ViewRender {
    private static final Pattern WORD_PATTERN = Pattern.compile("rend:\\{(.*?)\\}");
    private static final Pattern BLOCK_PATTERN = Pattern.compile("<custom:\\{body\\}>(.*?)</custom:\\{body\\}>");

    public static String createPage(ViewData viewData) throws IOException {
        String body = Files.readString(Paths.get(viewData.getPath()));
        body = renderWord(body, viewData.getMatchedData());
        body = renderBlock(body, viewData.getBlockData());
        return body;
    }

    public static String renderWord(String body, Map<String, String> matchedData) {
        Matcher matcher = WORD_PATTERN.matcher(body);
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

    public static String renderBlock(String body, Map<String, List<List<String>>> blockData) {
        Matcher matcher = BLOCK_PATTERN.matcher(body);
        StringBuilder bodySB = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        if (matcher.find()) {
            String result = matcher.group(1);
            sb.append("<tbody>").append(NEW_LINE);
            if (blockData.containsKey(result)) {
                List<List<String>> inputData = blockData.get(result);
                for (int i = 0; i < inputData.size(); i++) {
                    sb.append("<tr>").append(NEW_LINE);
                    sb.append("<th scope=\"row\">").append(i + 1).append("</th>").append(NEW_LINE);
                    List<String> data = inputData.get(i);
                    for (int j = 0; j < data.size(); j++) {
                        sb.append("<td>").append(data.get(j)).append("</td>").append(NEW_LINE);
                    }
                    sb.append("<td><a href=\"#\" class=\"btn btn-success\" role=\"button\">수정</a></td>").append(NEW_LINE);
                    sb.append("</tr>").append(NEW_LINE);
                }
            }
            sb.append("</tbody>").append(NEW_LINE);
            matcher.appendReplacement(bodySB, sb.toString());
        }
        matcher.appendTail(bodySB);
        return bodySB.toString();
    }
}
