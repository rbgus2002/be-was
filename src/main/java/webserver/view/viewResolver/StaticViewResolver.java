package webserver.view.viewResolver;

import webserver.view.view.StaticView;
import webserver.view.view.View;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StaticViewResolver implements ViewResolver {

    private static final String RESOURCES_TEMPLATES = "src/main/resources/templates/";

    @Override
    public View resolve(final String viewName) {
        Path templatesFilePath = Paths.get(RESOURCES_TEMPLATES, viewName);
        if (!Files.exists(templatesFilePath)) return null;
        return new StaticView(templatesFilePath.toString());
    }
}
