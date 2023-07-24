package container;

import webserver.HTTPServletResponse;

public class ViewResolver {

    private final String viewPath;
    private final HTTPServletResponse response;

    public ViewResolver(String viewPath, HTTPServletResponse response) {
        this.viewPath = viewPath;
        this.response = response;
    }

    public void service(){
        if (viewPath.contains("redirect:")) {
            String path = viewPath.substring(viewPath.indexOf(":") + 1);
            response.setHeader("Location", path);
            response.setStatusMessage("Found");
            response.setStatusCode("302");
            return;
        }


    }
}
