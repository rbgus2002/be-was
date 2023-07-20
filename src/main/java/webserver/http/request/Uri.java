package webserver.http.request;

import java.util.HashMap;
import java.util.Map;

import static utils.StringUtils.*;

public class Uri {
    private String path;
    private Query query;

    private Uri(String path, Query query) {
        this.path = path;
        this.query = query;
    }

    public static Uri from(String uri) {
        String[] tokens = uri.split(QUESTION_MARK);
        Query query = Query.from(tokens);
        return new Uri(tokens[0], query);
    }

    public String getPath() {
        return this.path;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(path);
        query.appendQueryString(stringBuilder);
        return stringBuilder.toString();
    }

    public boolean isSamePath(String path) {
        return this.path.equals(path);
    }
}
