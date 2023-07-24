package webserver.myframework.view;

public class DefaultDynamicContent extends DynamicContent {
    private final String content;

    public DefaultDynamicContent(String content) {
        this.content = content;
    }

    @Override
    String render() {
        return content;
    }
}
