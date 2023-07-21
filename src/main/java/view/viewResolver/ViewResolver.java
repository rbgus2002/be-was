package view.viewResolver;

import view.view.View;

import java.util.Optional;

public interface ViewResolver {
    Optional<View> resolve(final String viewName) throws Exception;
}
