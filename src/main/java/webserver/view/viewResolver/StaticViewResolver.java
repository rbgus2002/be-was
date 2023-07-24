package webserver.view.viewResolver;

import exception.InvalidPathException;
import webserver.view.view.StaticView;
import webserver.view.view.View;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StaticViewResolver implements ViewResolver {

    private static final String RESOURCES_TEMPLATES = "src/main/resources/templates/";

    @Override
    public View resolve(final String viewName) throws InvalidPathException {
        Path templatesFilePath = Paths.get(RESOURCES_TEMPLATES, viewName);
        if (!Files.exists(templatesFilePath)) throw new InvalidPathException();
        return new StaticView(templatesFilePath.toString());
    }
}
