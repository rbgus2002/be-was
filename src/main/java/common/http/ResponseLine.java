package common.http;

import common.enums.ResponseCode;

public class ResponseLine {
    private String version;
    private ResponseCode responseCode;

    public ResponseLine() {
    }

    public ResponseLine(String version) {
        this.version = version;
    }

    public ResponseLine(String version, ResponseCode responseCode) {
        this.version = version;
        this.responseCode = responseCode;
    }

    public void setVersion(String version) {
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
