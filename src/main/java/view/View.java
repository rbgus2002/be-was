package view;

import model.ContentType;
import model.HttpRequest;
import model.HttpResponse;
import model.HttpStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class View {

    private final String STATIC_PATH = "src/main/resources/static";
    private final String DYNAMIC_PATH = "src/main/resources/templates";
    private final String errorView = "src/main/resources/templates/404.html";

    private String view;
    private ContentType contentType;
    private HttpStatus status;
    private boolean isStaticValue;
    private String body;

    public View() {
        this.status = HttpStatus.NOT_FOUND;
        this.contentType = ContentType.TEXT_HTML;
        this.isStaticValue = false;
        this.body = null;
    }

    public void setView(String view) {
        this.view = view;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getView() {
        return view;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStaticValue(boolean value) {
        this.isStaticValue = value;
    }

    /**
     * Response에 렌더링한 view를 넣어줌. 이때 ModelAndView에 데이터가 존재하면 해당 데이터를 view에 삽입
     * @param request
     * @param response
     * @param ModelAndView
     */
    public void render(HttpRequest request, HttpResponse response, ModelAndView mv) throws IOException {
        if(contentType != null) {
            response.setContentType(contentType);
        }

        response.setStatus(status);


        if(status.getValue() / 100 == 3) {
            response.setHeader("Location", view);
            return;
        }

        Path path = findFilePath();
        if(!Files.exists(path)) {
            view = null;
        }
        response.setBody(Files.readAllBytes(path));

        //return이 object인 경우 바디에 담기 해결
    }

    private Path findFilePath() {
        StringBuilder pathBuilder = new StringBuilder();

        if(view == null) {
            return Paths.get(errorView);
        }

        if(isStaticValue) {
            pathBuilder.append(STATIC_PATH);
        }

        if(!isStaticValue) {
            pathBuilder.append(DYNAMIC_PATH);
        }

        pathBuilder.append(view);
        if(view.equals("/")) {
            pathBuilder.append("index");
        }

        if(!isStaticValue && !view.endsWith(".html")) {
            pathBuilder.append(".html");
        }

        return Paths.get(pathBuilder.toString());
    }
}