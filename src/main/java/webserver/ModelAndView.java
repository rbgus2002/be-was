package webserver;

import java.util.Map;

public class ModelAndView {

    private final String viewName;
    private final Map<String, Object> model;

    public ModelAndView(String viewName, Map<String, Object> model) {
        this.viewName = viewName;
        this.model = model;
    }

    public String getViewName() {
        return this.viewName;
    }

    public Map<String, Object> getModel() {
        return this.model;
    }
}
