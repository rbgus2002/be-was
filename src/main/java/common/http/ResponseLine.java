package common.http;

import common.enums.ResponseCode;

public class ResponseLine {
    private ResponseCode responseCode;

    public ResponseLine() {
    }

    public ResponseLine(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }


    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    @Override
    public String toString() {
        return responseCode.getDescription();
    }

}
