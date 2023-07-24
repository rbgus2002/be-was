package webserver.myframework.view.content;

import webserver.myframework.model.Model;
import webserver.myframework.utils.ReflectionUtils;
import webserver.myframework.utils.StringUtils;

import java.util.List;

public abstract class DynamicContent {
   public abstract String render() throws NoSuchFieldException, IllegalAccessException;

    public static DynamicContent getDynamicContent(Model model, String objectName, String content) {
        if (objectName.startsWith("default")) {
            return new DefaultDynamicContent(content);
        }

        List<String> contentParts = StringUtils.splitStringByRegex(content, "(\\[[^\\]]+\\])");
        if (objectName.startsWith("list-")) {
            return getMultipleObjectDynamicContent(model, objectName, contentParts);
        }

        return getSingleObjectDynamicContent(model, objectName, contentParts);
    }

    private static SingleObjectDynamicContent getSingleObjectDynamicContent(Model model,
                                                                            String objectName,
                                                                            List<String> contentParts) {
        Object parameter = model.getParameter(objectName);
        if (parameter == null) {
            return null;
        }
        return new SingleObjectDynamicContent(parameter, contentParts);
    }

    @SuppressWarnings("unchecked")
    private static MultipleObjectDynamicContent getMultipleObjectDynamicContent(Model model,
                                                                                String objectName,
                                                                                List<String> contentParts) {
        objectName = objectName.replace("list-", "").trim();
        List<Object> parameters = (List<Object>) model.getParameter(objectName);
        if (parameters == null) {
            return null;
        }
        return new MultipleObjectDynamicContent(parameters, contentParts);
    }

    protected static void writeParameterContent(StringBuilder stringBuilder,
                                                List<String> contentParts,
                                                Object parameter) throws NoSuchFieldException, IllegalAccessException {
        for (String contentPart : contentParts) {
            if (contentPart.startsWith("[") && contentPart.endsWith("]")) {
                String fieldName = contentPart.replaceAll("[\\[\\]]", "");
                Object field = ReflectionUtils.getField(parameter, fieldName);
                stringBuilder.append(field);
                continue;
            }
            stringBuilder.append(contentPart);
        }
    }
}
