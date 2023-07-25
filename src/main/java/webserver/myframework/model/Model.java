package webserver.myframework.model;

public interface Model {
    void addParameter(String parameterName, Object parameter);
    Object getParameter(String parameterName);
}
