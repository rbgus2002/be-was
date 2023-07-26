package controller;

import model.Model;

public class ModelAndView {
    private final String viewPath;
    private final Model model;

    public ModelAndView(String viewPath) {
        this.viewPath = viewPath;
        model = null;
    }

    public ModelAndView(String viewPath, Model model) {
        this.viewPath = viewPath;
        this.model = model;
    }

    public String getViewPath() {
        return viewPath;
    }
    public Model getModel() {
        return model;
    }
}
