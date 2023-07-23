package webserver.request;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RequestQuery {

    private final Map<String, String> queries;

    private RequestQuery(Map<String, String> queries) {
        this.queries = queries;
    }

    public static RequestQuery of(String string) {

        String[] array = string.split("&");

        Map<String, String> queries = Stream.of(array)
                .collect(Collectors.toMap(
                        s -> s.split("=")[0],
                        s -> s.split("=")[1]
                ));

        return new RequestQuery(queries);
    }

    public Optional<String> getValue(String key) {
        String value = queries.get(key);
        if(value == null) return Optional.empty();

        return Optional.of(queries.get(key));
    }
}
