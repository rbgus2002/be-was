package view;

import common.enums.ResponseCode;
import common.http.HttpRequest;
import common.http.HttpResponse;

import java.util.Map;

public interface View {

    String getContentType();

    void render(Map<String, Object> model, HttpRequest request, HttpResponse response) throws Exception;

    default void decorateResponse(HttpResponse response, ResponseCode responseCode, byte[] body) {
        response.setResponseLine(responseCode);
        response.addHeader("Content-Type", getContentType());
        response.addHeader("Content-Length", String.valueOf(body.length));
        response.setBody(body);
    }

}
