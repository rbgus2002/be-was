package view.viewResolver;

import view.view.StaticView;
import view.view.View;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class StaticViewResolver implements ViewResolver {

    private static final String resourcePath = "src/main/resources/templates/";

    @Override
    public Optional<View> resolve(final String viewName) {
        Path filePath = Paths.get(resourcePath, viewName);

        if (!Files.exists(filePath)) {
            return Optional.empty();
        }

        return Optional.of(new StaticView(filePath.toString()));
    }
}
