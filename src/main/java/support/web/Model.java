package support.web;

import java.util.HashMap;
import java.util.Map;

public class Model {

    private final Map<String, Object> model = new HashMap<>();

    public Object getAttribute(String key) {
        return model.get(key);
    }

    public Model addAttribute(String key, Object attribute) {
        model.put(key, attribute);
        return this;
    }

}
