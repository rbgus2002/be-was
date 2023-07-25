package webserver.myframework.view.content;

import java.util.List;

public class SingleObjectDynamicContent extends DynamicContent{
    private final Object parameter;
    private final List<String> contentParts;

    public SingleObjectDynamicContent(Object parameter, List<String> contentParts) {
        this.parameter = parameter;
        this.contentParts = contentParts;
    }

    @Override
    public String render() throws NoSuchFieldException, IllegalAccessException {
        StringBuilder stringBuilder = new StringBuilder();
        writeParameterContent(stringBuilder, contentParts, parameter);
        return stringBuilder.toString();
    }
}
