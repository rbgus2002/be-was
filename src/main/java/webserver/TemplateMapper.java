package webserver;

import annotation.RequestMapping;
import annotation.TemplateMapping;
import template.DynamicTemplate;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static webserver.ServerConfig.CONTROLLER_CLASS;

public class TemplateMapper {

    private static final TemplateMapper INSTANCE = new TemplateMapper();

    private final Map<String, DynamicTemplate> map;

    private TemplateMapper() {
        map = new HashMap<>();
    }

    public static TemplateMapper getInstance() {
        return INSTANCE;
    }

    public void initialize() throws Exception {
        Method[] methods = CONTROLLER_CLASS.getMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(TemplateMapping.class)) {
                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                TemplateMapping templateMapping = method.getAnnotation(TemplateMapping.class);

                if (requestMapping == null) continue;

                Class<? extends DynamicTemplate> clazz = templateMapping.name();

                String path = requestMapping.path();
                DynamicTemplate dynamicRenderer = clazz.getDeclaredConstructor().newInstance();

                map.put(path, dynamicRenderer);
            }
        }
    }

    public DynamicTemplate getDynamicTemplate(String path) {
        return map.get(path);
    }

    public boolean contains(String path) {
        return map.containsKey(path);
    }
}
