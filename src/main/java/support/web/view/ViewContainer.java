package support.web.view;

import support.annotation.AutoInject;
import support.annotation.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class ViewContainer {

    private final List<View> viewContainer = new ArrayList<>();

    public ViewContainer(@AutoInject ErrorView errorView,
                         @AutoInject IndexView indexView,
                         @AutoInject PostShowView postShowView,
                         @AutoInject UserListView userListView) {
        viewContainer.add(errorView);
        viewContainer.add(indexView);
        viewContainer.add(postShowView);
        viewContainer.add(userListView);
    }

    public View getViewByName(String name) {
        return viewContainer.stream()
                .filter(view -> Objects.equals(view.getName(), name))
                .findAny()
                .orElse(null);
    }

}
