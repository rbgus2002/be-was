package controller;

public class ModelAndView {
    private final String viewPath;
    //TODO: Model Class 만들어보기 (Model이 여러 개 들어올 수 있으므로)
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
