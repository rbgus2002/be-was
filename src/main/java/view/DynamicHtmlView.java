package view;

import common.enums.ContentType;
import common.enums.ResponseCode;
import common.http.HttpRequest;
import common.http.HttpResponse;
import dynamic.DynamicHtml;
import utils.FileUtils;

import java.util.Map;

public class DynamicHtmlView implements View {

    private final String viewPath;
    private final DynamicHtml dynamicHtml;

    public DynamicHtmlView(String viewPath, DynamicHtml dynamicHtml) {
        this.viewPath = viewPath;
        this.dynamicHtml = dynamicHtml;
    }

    @Override
    public ContentType getContentType() {
        return ContentType.HTML;
    }

    @Override
    public void render(Map<String, Object> model, HttpRequest request, HttpResponse response) throws Exception {
        String html = FileUtils.readFileToString(viewPath);
        html = dynamicHtml.decorateHeader(html, model);
        html = dynamicHtml.decorate(html, model);

        response.setUpDefaultResponse(ResponseCode.OK, getContentType(), html.getBytes());
    }

}
