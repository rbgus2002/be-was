package view;

import common.enums.ContentType;
import common.http.HttpRequest;
import common.http.HttpResponse;

import java.util.Map;

public class RedirectView implements View {
    private final String redirectPath;

    public RedirectView(String redirectPath) {
        this.redirectPath = redirectPath;
    }

    @Override
    public ContentType getContentType() {
        return ContentType.NONE;
    }

    @Override
    public void render(Map<String, Object> model, HttpRequest request, HttpResponse response) {
        response.setUpRedirectResponse(redirectPath);
    }

}
