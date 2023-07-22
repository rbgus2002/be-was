package common.http;

import common.wrapper.Queries;

import java.util.Optional;

public class RequestBody {
    private final String source;

    public RequestBody(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public Optional<Queries> getQueries() {
        if (source != null && !source.isEmpty()) {
            return Optional.of(new Queries(source));
        }

        return Optional.empty();
    }

}
