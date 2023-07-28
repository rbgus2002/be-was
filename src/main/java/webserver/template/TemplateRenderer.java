package webserver.template;

import webserver.model.Model;
import webserver.utils.FileUtils;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateRenderer {
    private static TemplateRenderer templateRenderer;

    private TemplateRenderer() {
    }

    public static TemplateRenderer getInstance() {
        if (templateRenderer == null) {
            templateRenderer = new TemplateRenderer();
        }
        return templateRenderer;
    }

    public String render(String html, Model model) {
        html = renderChange(html);
        html = renderLoad(html, model);
        html = renderFor(html, model);
        return renderIf(html, model);
    }

    public String renderChange(String html) {
        StringBuilder htmlBuilder = new StringBuilder();
        int beforeIdx = 0;
        while (true) {
            int start = html.indexOf("[change: to=\"", beforeIdx);
            if (start == -1) {
                break;
            }
            int end = html.indexOf("\"/]", start) + 3;

            String path = html.substring(start, end)
                    .replace("[change: to=\"", "")
                    .replace("\"/]", "");
            byte[] htmlFile = FileUtils.readFileFromTemplate(path);

            htmlBuilder.append(html, beforeIdx, start).append(new String(htmlFile));
            beforeIdx = end;
        }
        htmlBuilder.append(html, beforeIdx, html.length());
        return htmlBuilder.toString();
    }

    public String renderLoad(String html, Model model) {
        StringBuilder htmlBuilder = new StringBuilder();
        int beforeIdx = 0;
        while (true) {
            int start = html.indexOf("[load:", beforeIdx);
            if (start == -1) {
                break;
            }
            int end = html.indexOf("/]", start) + 2;

            String key = html.substring(start, end).replace("[load: ", "").replace("/]", "");
            String attribute = (String) model.getAttribute(key);

            htmlBuilder.append(html, beforeIdx, start).append(attribute);
            beforeIdx = end;
        }
        htmlBuilder.append(html, beforeIdx, html.length());
        return htmlBuilder.toString();
    }

    public String renderIf(String html, Model model) {
        String openIfRegex = "\\[if: (.*?)\\]";
        String closeIfRegex = "\\[/if\\]";

        Pattern openIfPattern = Pattern.compile(openIfRegex);
        Pattern closeIfPattern = Pattern.compile(closeIfRegex);

        Matcher openIfMatcher = openIfPattern.matcher(html);
        Matcher closeIfMatcher = closeIfPattern.matcher(html);

        StringBuilder htmlBuilder = new StringBuilder();
        int beforeIdx = 0;
        while (openIfMatcher.find() && closeIfMatcher.find()) {
            String ifTag = openIfMatcher.group();
            htmlBuilder.append(html, beforeIdx, openIfMatcher.start());
            if (isOk(ifTag, model)) {
                htmlBuilder.append(html, openIfMatcher.end(), closeIfMatcher.start());
            }
            beforeIdx = closeIfMatcher.end();
        }
        htmlBuilder.append(html, beforeIdx, html.length());
        return htmlBuilder.toString();
    }

    private boolean isOk(String ifTag, Model model) {
        String condition = getIfCondition(ifTag);
        String[] tokens = condition.split("=");

        return model.getAttribute(tokens[0]).equals(tokens[1]);
    }

    private static String getIfCondition(String ifTag) {
        return ifTag.replace("[if: ", "").replace("\"", "").replace("]", "");
    }

    public String renderFor(String html, Model model) {
        String openIfRegex = "\\[for: (.*?)\\]";
        String closeIfRegex = "\\[/for\\]";

        Pattern openIfPattern = Pattern.compile(openIfRegex);
        Pattern closeIfPattern = Pattern.compile(closeIfRegex);

        Matcher openIfMatcher = openIfPattern.matcher(html);
        Matcher closeIfMatcher = closeIfPattern.matcher(html);

        StringBuilder htmlBuilder = new StringBuilder();
        int beforeIdx = 0;
        while (openIfMatcher.find() && closeIfMatcher.find()) {
            String forTag = openIfMatcher.group();
            String modelName = getForName(forTag);
            htmlBuilder.append(html, beforeIdx, openIfMatcher.start());

            String snippet = html.substring(openIfMatcher.end(), closeIfMatcher.start());

            List<Map<String, String>> dataMap = (List<Map<String, String>>) model.getAttribute(modelName);
            for (int index = 1; index <= dataMap.size(); index++) {
                Map<String, String> data = dataMap.get(index - 1);
                String renderedHtml = snippet.replace("[index]", String.valueOf(index));
                for (String key : data.keySet()) {
                    renderedHtml = renderedHtml.replace(("[" + key + "]"), data.get(key));
                }
                htmlBuilder.append(renderedHtml);
            }
            beforeIdx = closeIfMatcher.end();
        }
        htmlBuilder.append(html, beforeIdx, html.length());
        return htmlBuilder.toString();
    }

    private String getForName(String forTag) {
        return forTag.replace("[for: ", "").replace("]", "");
    }
}
