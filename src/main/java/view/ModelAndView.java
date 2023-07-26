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

    private String view;

    private Map<String, Object> modelMap;

    private ContentType contentType;

    private HttpStatus status;

    public ModelAndView() {
        this.modelMap = new HashMap<>();
        this.status = HttpStatus.NOT_FOUND;
        this.view = null;
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

    public HttpStatus getStatus() {
        return this.status;
    }

}
