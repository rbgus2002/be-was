package view;

import common.enums.ContentType;
import common.enums.ResponseCode;
import common.http.HttpRequest;
import common.http.HttpResponse;
import view.renderer.HtmlRenderer;
import utils.FileUtils;

import java.util.Map;

public class DynamicHtmlView implements View {

    private final String viewPath;
    private final HtmlRenderer htmlRenderer;

    public DynamicHtmlView(String viewPath, HtmlRenderer htmlRenderer) {
        this.viewPath = viewPath;
        this.htmlRenderer = htmlRenderer;
    }

    @Override
    public ContentType getContentType() {
        return ContentType.HTML;
    }

    @Override
    public void render(Map<String, Object> model, HttpRequest request, HttpResponse response) {
        String html = FileUtils.readFileToString(viewPath);
        html = htmlRenderer.decorateHeaderBar(html, model);
        html = htmlRenderer.decorate(html, model);

        response.setUpDefaultResponse(ResponseCode.OK, getContentType(), html.getBytes());
    }

}
