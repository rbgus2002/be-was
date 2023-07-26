package view;

import common.enums.ContentType;
import common.enums.ResponseCode;
import common.http.HttpRequest;
import common.http.HttpResponse;
import view.template.DynamicTemplate;
import utils.FileUtils;

import java.util.Map;

public class DynamicHtmlView implements View {

    private final String viewPath;
    private final DynamicTemplate dynamicRenderer;

    public DynamicHtmlView(String viewPath, DynamicTemplate dynamicRenderer) {
        this.viewPath = viewPath;
        this.dynamicRenderer = dynamicRenderer;
    }

    @Override
    public ContentType getContentType() {
        return ContentType.HTML;
    }

    @Override
    public void render(Map<String, Object> model, HttpRequest request, HttpResponse response) throws Exception {
        String html = FileUtils.readFileToString(viewPath);
        html = dynamicRenderer.decorateHeaderBar(html, model);
        html = dynamicRenderer.decorate(html, model);

        response.setUpDefaultResponse(ResponseCode.OK, getContentType(), html.getBytes());
    }

}
