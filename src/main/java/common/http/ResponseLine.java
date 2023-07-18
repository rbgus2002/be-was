package common.http;

import common.enums.ResponseCode;

public class ResponseLine {
    private final String version;
    private ResponseCode responseCode;

    public ResponseLine(String version) {
        this.version = version;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    @Override
    public String toString() {
        return version + " " + responseCode.getDescription();
    }
}