package support.web;

public class ModelAndView {

    private String viewName;
    private Model model;

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Model getModel() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public Object getAttribute(String key) {
        return getModel().getAttribute(key);
    }

    public ModelAndView addAttribute(String key, Object attribute) {
        getModel().addAttribute(key, attribute);
        return this;
    }

}
