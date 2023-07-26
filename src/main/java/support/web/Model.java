package support.web;

import java.util.HashMap;

public class Model extends HashMap<String, Object> {

    public Object getAttribute(String key) {
        return get(key);
    }

    public Model addAttribute(String key, Object attribute) {
        put(key, attribute);
        return this;
    }

}
