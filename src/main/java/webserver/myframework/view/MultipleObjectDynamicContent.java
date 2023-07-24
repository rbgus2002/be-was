package webserver.myframework.view;

import java.util.List;

public class MultipleObjectDynamicContent extends DynamicContent {
    private final List<Object> parameters;
    private final List<String> contentParts;

    public MultipleObjectDynamicContent(List<Object> parameters, List<String> contentParts) {
        this.parameters = parameters;
        this.contentParts = contentParts;
    }

    @Override
    String render() throws NoSuchFieldException, IllegalAccessException {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object parameter : parameters) {
            writeParameterContent(stringBuilder, contentParts, parameter);
        }
        return stringBuilder.toString();
    }
}
