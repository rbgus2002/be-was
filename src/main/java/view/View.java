package view;

import common.enums.ResponseCode;
import common.http.HttpRequest;
import common.http.HttpResponse;
import common.http.ResponseLine;

import java.util.HashMap;
import java.util.Map;

public interface View {

    String getContentType();

    void render(Map<String, Object> model, HttpRequest request, HttpResponse response) throws Exception;

    // TODO : 뷰에서 reponse를 세팅하면 안 되지 싶다. 개선하자
    default void decorateResponse(HttpRequest request, HttpResponse response, ResponseCode responseCode, byte[] body) {
        ResponseLine responseLine = new ResponseLine(request.getVersion(), responseCode);

        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", getContentType());
        header.put("Content-Length", String.valueOf(body.length));

        response.setResponseLine(responseLine);
        response.setHeaders(header);
        response.setBody(body);
    }

}
