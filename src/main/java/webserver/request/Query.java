package webserver.request;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Query {

    private final Map<String, String> queries;

    private Query(Map<String, String> queries) {
        this.queries = queries;
    }

    public static Query of(String string) {

        String[] array = string.split("&");

        Map<String, String> queries = Stream.of(array)
                .collect(Collectors.toMap(
                        s -> s.split("=")[0],
                        s -> s.split("=")[1]
                ));

        return new Query(queries);
    }
}
