package support.web;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {

    private String viewName;
    private final Map<String, Object> model = new HashMap<>();

    public String getViewName() {
        return viewName;
    }

    public Object getAttribute(String key) {
        return model.get(key);
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public void addObject(String key, Object attribute) {
        model.put(key, attribute);
    }

}
