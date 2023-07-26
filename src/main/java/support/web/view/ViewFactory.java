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

    // TODO: 이름 mapper 추가
    public Class<? extends View> nameConverter(String name) {
        if (name.equals("/user/list")) {
            return UserListView.class;
        }
        return null;
    }

}
