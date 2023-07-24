package webserver.myframework.model;

import java.util.HashMap;
import java.util.Map;

public class ModelImpl implements Model {
    private final Map<String, Object> parameters = new HashMap<>();

    @Override
    public void addParameter(String parameterName, Object parameter) {
        parameters.put(parameterName, parameter);
    }

    @Override
    public Object getParameter(String parameterName) {
        return parameters.get(parameterName);
    }
}
