package webserver.myframework.view;


import webserver.myframework.model.Model;

import java.io.IOException;

public interface ViewResolver {
    View resolve(String viewUri, Model model) throws IOException;
}
