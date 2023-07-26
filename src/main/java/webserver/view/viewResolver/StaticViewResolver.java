package webserver.view.viewResolver;

import webserver.view.view.StaticView;
import webserver.view.view.View;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class StaticViewResolver implements ViewResolver {

    private static final String RESOURCES_TEMPLATES = "src/main/resources/static/";
    private static final String RESOURCES_STATIC = "src/main/resources/templates/";

    @Override
    public Optional<View> resolve(final String viewName) {
        Path templatesFilePath = Paths.get(RESOURCES_TEMPLATES, viewName);

        if (!Files.exists(templatesFilePath)) {
            templatesFilePath = Paths.get(RESOURCES_STATIC, viewName);
        }

        if (!Files.exists(templatesFilePath)) {
            return Optional.empty();
        }

        return Optional.of(new StaticView(templatesFilePath.toString()));
    }
}
