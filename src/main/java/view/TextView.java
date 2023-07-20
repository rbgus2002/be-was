package view;

import common.enums.ContentType;
import common.enums.ResponseCode;
import common.http.HttpRequest;
import common.http.HttpResponse;

import java.util.Map;

public class TextView implements View {
    private final String text;

    public TextView(String text) {
        this.text = text;
    }

    @Override
    public String getContentType() {
        return ContentType.PLAIN.getDescription();
    }

    @Override
    public void render(Map<String, Object> model, HttpRequest request, HttpResponse response) throws Exception{
        decorateResponse(request, response, ResponseCode.OK, text.getBytes());
    }

}
