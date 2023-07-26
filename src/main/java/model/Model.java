package model;

import java.util.HashMap;
import java.util.Map;

public class Model {
    private final Map<String, Object> model;

    public Model() {
        this.model = new HashMap<>();
    }

    public void addAttribute(String key, Object value) {
        model.put(key, value);
    }

    public Object getValue(String key) {
        return model.get(key);
    }
}
