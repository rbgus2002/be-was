package webserver.http.request;

import java.util.HashMap;
import java.util.Map;

import static utils.StringUtils.*;

public class Uri {
    String path;
    Map<String, String> query;

    private Uri(String path, Map<String, String> query) {
        this.path = path;
        this.query = query;
    }

    public static Uri from(String uri) {
        String[] tokens = uri.split(QUESTION_MARK);
        Map<String, String> query = new HashMap<>();

        if (hasQuery(tokens)) {
            String[] pairs = tokens[1].split(AMPERSAND);
            for (String pair : pairs) {
                String[] keyValue = pair.split(EQUAL);
                query.put(keyValue[0], keyValue[1]);
            }
        }
        return new Uri(tokens[0], query);
    }

    private static boolean hasQuery(String[] tokens) {
        return tokens.length > 1;
    }

    public String getPath() {
        return this.path;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(path);
        appendQueryString(stringBuilder);
        return stringBuilder.toString();
    }

    private void appendQueryString(StringBuilder stringBuilder) {
        if (!query.isEmpty()) {
            stringBuilder.append("?");
            query.forEach((key, value) -> stringBuilder.append(key + EQUAL + value + AMPERSAND));
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
    }
}
