package modelview;

import java.util.HashMap;
import java.util.Map;

public class ModelView {
    private final String viewName;
    private final Map<String, Object> model;

    public ModelView(String viewName) {
        this.viewName = viewName;
        this.model = new HashMap<>();
    }

    public String getViewName() {
        return viewName;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void addModelAttribute(String key, Object value) {
        model.put(key, value);
    }

}
