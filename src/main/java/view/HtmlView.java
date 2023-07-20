package view;

import common.enums.ContentType;
import common.enums.ResponseCode;
import common.http.HttpRequest;
import common.http.HttpResponse;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;

public class HtmlView implements View {
    private final String viewPath;

    public HtmlView(String viewPath) {
        this.viewPath = viewPath;
    }

    @Override
    public String getContentType() {
        return ContentType.HTML.getDescription();
    }

    @Override
    public void render(Map<String, Object> model, HttpRequest request, HttpResponse response) throws Exception {
        byte[] body = Files.readAllBytes(new File(viewPath).toPath());
        decorateResponse(request, response, ResponseCode.OK, body);
    }

}
