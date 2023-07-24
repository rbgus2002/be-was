package view;

import common.enums.ContentType;
import common.enums.ResponseCode;
import common.http.HttpRequest;
import common.http.HttpResponse;

import java.util.Map;

public class PlainTextView implements View {
    private final String text;

    public PlainTextView(String text) {
        this.text = text;
    }

    @Override
    public ContentType getContentType() {
        return ContentType.PLAIN;
    }

    @Override
    public void render(Map<String, Object> model, HttpRequest request, HttpResponse response) {
        response.setUpDefaultResponse(ResponseCode.OK, getContentType(), text.getBytes());
    }

}
