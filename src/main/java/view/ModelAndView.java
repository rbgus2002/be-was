package view;

import model.ContentType;
import model.HttpRequest;
import model.HttpResponse;
import model.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HandlerAdapter;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {

    private static final Logger logger = LoggerFactory.getLogger(ModelAndView.class);

    private final String errorView = "src/main/resources/templates/404.html";
    private final String DYNAMIC_PATH = "src/main/resources/templates";


    private String view;

    private Map<String, Object> modelMap;

    private ContentType contentType;

    private HttpStatus status;

    public ModelAndView() {
        this.modelMap = new HashMap<>();
        this.status = HttpStatus.NOT_FOUND;
        this.view = errorView;
    }

    public String getView() {
        return view;
    }

    public Map<String, Object> getModelMap() {
        return modelMap;
    }

    public void setViewName(String view) {
        this.view = view;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void addObject(String name, Object value) {
        modelMap.put(name, value);
    }


    public void setResponse(HttpResponse response) {
        try {
            if(contentType != null) {
                response.setContentType(contentType);
            }
            response.setStatus(status);

            //3xx Response일 때
            if(status.getValue() / 100 == 3) {
                response.setHeader("Location", view.substring(DYNAMIC_PATH.length()));
                return;
            }
            response.setBody(Files.readAllBytes(Paths.get(view)));

        } catch (Exception e) {
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setContentType(model.ContentType.TEXT_PLAIN);
            logger.error(e.getMessage());
        }
    }
}
