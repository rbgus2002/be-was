package webserver.view.viewResolver;

import webserver.view.view.View;

public interface ViewResolver {
    View resolve(final String viewName) throws Exception;
}
