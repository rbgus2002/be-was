package webserver.view.viewResolver;

import exception.InvalidPathException;
import webserver.view.view.StaticView;
import webserver.view.view.View;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StaticViewResolver implements ViewResolver {

    private static final String resourcePath = "src/main/resources/templates/";

    @Override
    public View resolve(final String viewName) {
        Path filePath = Paths.get(resourcePath, viewName);

        if (!Files.exists(filePath)) throw new InvalidPathException();

        return new StaticView(filePath.toString());
    }
}
