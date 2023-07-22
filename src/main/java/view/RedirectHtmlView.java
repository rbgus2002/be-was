package view;

import common.enums.ContentType;
import common.enums.ResponseCode;
import common.http.HttpRequest;
import common.http.HttpResponse;

import java.util.Map;

public class RedirectHtmlView implements View {
    private final String viewPath;

    public RedirectHtmlView(String viewPath) {
        this.viewPath = viewPath;
    }

    @Override
    public String getContentType() {
        return ContentType.HTML.getDescription();
    }

    @Override
    public void render(Map<String, Object> model, HttpRequest request, HttpResponse response) throws Exception {
        decorateResponse(request, response, ResponseCode.FOUND, new byte[0]);
    }

}
