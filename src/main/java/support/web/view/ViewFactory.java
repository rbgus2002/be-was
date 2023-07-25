package support.web.view;

import support.annotation.Container;
import support.instance.DefaultInstanceManager;

@Container
public class ViewFactory {

    private final DefaultInstanceManager defaultInstanceManager = DefaultInstanceManager.getInstanceMagager();

    public View getViewByName(String name) {
        Class<? extends View> viewClass = nameConverter(name);
        if (viewClass == null) {
            throw new RuntimeException("잘못된 뷰 이름입니다.");
        }
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
