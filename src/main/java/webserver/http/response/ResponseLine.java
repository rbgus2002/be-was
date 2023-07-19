package webserver.http.response;

import static webserver.http.Http.SPACING_DELIMITER;

import webserver.http.Http.StatusCode;
import webserver.http.Http.Version;

public class ResponseLine {
    private final Version version;
    private final StatusCode code;

    public ResponseLine(final StatusCode code) {
        this(Version.DEFAULT, code);
    }

    public ResponseLine(final Version version, final StatusCode code) {
        this.version = version;
        this.code = code;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(version.getValue())
                .append(SPACING_DELIMITER)
                .append(code.getValue())
                .append(SPACING_DELIMITER)
                .append(code.getCode())
                .toString();
    }
}
