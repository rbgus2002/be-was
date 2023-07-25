package webserver.request;

import exception.badRequest.MissingParameterException;

import java.util.Map;
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

    public String getValue(final String key) {
        String value = queries.get(key);
        if (value == null) throw new MissingParameterException();

        return queries.get(key);
    }
}
