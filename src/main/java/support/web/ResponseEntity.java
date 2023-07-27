package support.web;

import webserver.Header;
import webserver.response.HttpStatus;

public class ResponseEntity<T> {

    private HttpEntity httpEntity;
    private T returnValue;

    public ResponseEntity(HttpStatus httpStatus) {
        this(httpStatus, null, null);
    }

    public ResponseEntity(HttpStatus httpStatus, T returnValue) {
        this(httpStatus, null, returnValue);
    }

    public ResponseEntity(HttpStatus httpStatus, Header header) {
        this(httpStatus, header, null);
    }

    public ResponseEntity(HttpStatus httpStatus, Header header, T returnValue) {
        this.httpEntity = new HttpEntity(httpStatus, header);
        this.returnValue = returnValue;
    }

    public HttpEntity getHttpEntity() {
        return httpEntity;
    }

    public T getReturnValue() {
        return returnValue;
    }

    public Class<?> getReturnType() {
        return returnValue == null ? void.class : returnValue.getClass();
    }

}
