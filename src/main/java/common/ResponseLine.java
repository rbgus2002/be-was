package common;

public class ResponseLine {
    private final String version;
    private final ResponseCode responseCode;

    public ResponseLine(String version, ResponseCode responseCode) {
        this.version = version;
        this.responseCode = responseCode;
    }

    @Override
    public String toString() {
        return version + " " + responseCode.getDescription();
    }
}
