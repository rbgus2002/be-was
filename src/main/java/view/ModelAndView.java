package view;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {

    private String view;

    private Map<String, Object> modelMap;

    public ModelAndView() {
        modelMap = new HashMap<>();
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

    public void addObject(String name, Object value) {
        modelMap.put(name, value);
    }
}
