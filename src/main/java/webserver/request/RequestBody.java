package webserver.request;

import exception.badRequest.MissingParameterException;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RequestBody {

    private final Map<String, String> parameters;

    private RequestBody(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public static RequestBody of(String string) {

        String[] array = string.split("&");

        Map<String, String> queries = Stream.of(array)
                .collect(Collectors.toMap(
                        s -> s.split("=")[0],
                        s -> s.split("=")[1]
                ));

        return new RequestBody(queries);
    }

    public String getValue(final String key) {
        String value = parameters.get(key);
        if (value == null) throw new MissingParameterException();

        return parameters.get(key);
    }
}
