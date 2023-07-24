package webserver.http;

import java.util.ArrayList;
import java.util.List;

public class Cookie {
    private final List<String> directives;

    public Cookie() {
        this.directives = new ArrayList<>();
    }

    public void add(String directive) {
        directives.add(directive);
    }

    public String get(String name) {
        for (String directive : directives) {
            String[] tokens = directive.split("=");
            if (tokens.length == 2 && tokens[0].equals(name)) {
                return tokens[1];
            }
        }
        return "";
    }

    public String getMessage() {
        return String.join("; ", directives);
    }
}
