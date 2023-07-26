package view;

import common.enums.ContentType;
import common.enums.ResponseCode;
import common.http.HttpRequest;
import common.http.HttpResponse;
import utils.FileUtils;

import java.util.Map;

public class HtmlView implements View {
    private final String viewPath;

    public HtmlView(String viewPath) {
        this.viewPath = viewPath;
    }

    @Override
    public ContentType getContentType() {
        return ContentType.HTML;
    }

    @Override
    public void render(Map<String, Object> model, HttpRequest request, HttpResponse response) throws Exception {
        byte[] body = FileUtils.readFileToBytes(viewPath);
        response.setUpDefaultResponse(ResponseCode.OK, getContentType(), body);
    }

}
