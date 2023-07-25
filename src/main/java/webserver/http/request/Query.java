package webserver.http.request;

import java.util.LinkedHashMap;
import java.util.Map;

import static utils.StringUtils.AMPERSAND;
import static utils.StringUtils.EQUAL;

public class Query {
    private Map<String, String> query;

    private Query(Map<String, String> query) {
        this.query = query;
    }

    public static Query from(String[] tokens) {
        Map<String, String> query = new LinkedHashMap<>();

        if (hasQuery(tokens)) {
            String[] pairs = tokens[1].split(AMPERSAND);
            for (String pair : pairs) {
                String[] keyValue = pair.split(EQUAL);
                query.put(keyValue[0], keyValue[1]);
            }
        }
        return new Query(query);
    }

    private static boolean hasQuery(String[] tokens) {
        return tokens.length > 1;
    }

    public void appendQueryString(StringBuilder stringBuilder) {
        if (!query.isEmpty()) {
            stringBuilder.append("?");
            query.forEach((key, value) -> stringBuilder.append(key + EQUAL + value + AMPERSAND));
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
    }
}
