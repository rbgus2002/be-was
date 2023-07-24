package webserver.myframework.view.content;

public class DefaultDynamicContent extends DynamicContent {
    private final String content;

    public DefaultDynamicContent(String content) {
        this.content = content;
    }

    @Override
    public String render() {
        return content;
    }
}
