package webserver;

public class ModelAndView {
    private final String viewPath;
    private final Object model;

    public ModelAndView(String viewPath, Object model) {
        this.viewPath = viewPath;
        this.model = model;
    }

    public String getViewPath() {
        return viewPath;
    }

    public Object getModel() {
        return model;
    }
}
