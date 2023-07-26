package support.web.view;

import support.annotation.Container;
import support.instance.DefaultInstanceManager;

@Container
public class ViewFactory {

    private final DefaultInstanceManager defaultInstanceManager = DefaultInstanceManager.getInstanceMagager();

    public View getViewByName(String name) {
        Class<? extends View> viewClass = nameConverter(name);
        return defaultInstanceManager.getInstance(viewClass);
    }

    public View getErrorView() {
        return defaultInstanceManager.getInstance(ErrorView.class);
    }

    // TODO: 이름 mapper 추가
    public Class<? extends View> nameConverter(String name) {
        if ("/user/list".equals(name)) {
            return UserListView.class;
        } else if ("/index".equals(name)) {
            return IndexView.class;
        } else if ("/post/show".equals(name)) {
            return PostShowView.class;
        }
        return null;
    }

}
