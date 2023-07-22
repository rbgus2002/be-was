package view;

import common.enums.ContentType;
import common.enums.ResponseCode;
import common.http.HttpRequest;
import common.http.HttpResponse;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;

import static webserver.ServerConfig.ERROR_PAGE;

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

        if (viewPath.endsWith(ERROR_PAGE)) {
            decorateResponse(response, ResponseCode.BAD_REQUEST, body);
        }
        else {
            decorateResponse(response, ResponseCode.OK, body);
        }

    }

}
