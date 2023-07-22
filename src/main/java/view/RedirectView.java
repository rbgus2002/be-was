package view;

import common.enums.ContentType;
import common.enums.ResponseCode;
import common.http.HttpRequest;
import common.http.HttpResponse;

import java.util.Map;

public class RedirectView implements View {
    private final String viewPath;

    public RedirectView(String viewPath) {
        this.viewPath = viewPath;
    }

    @Override
    public String getContentType() {
        return ContentType.HTML.getDescription();
    }

    @Override
    public void render(Map<String, Object> model, HttpRequest request, HttpResponse response) throws Exception {
        decorateResponse(response, ResponseCode.FOUND, new byte[0]);
        response.addHeader("Location", viewPath);
    }

}
