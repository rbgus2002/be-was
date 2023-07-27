package webserver;

import annotation.RequestMapping;
import annotation.RendererMapping;
import view.renderer.HtmlRenderer;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static webserver.ServerConfig.CONTROLLER_CLASS;

public class RendererMapper {

    private static final RendererMapper INSTANCE = new RendererMapper();

    private final Map<String, HtmlRenderer> map;

    private RendererMapper() {
        map = new HashMap<>();
    }

    public static RendererMapper getInstance() {
        return INSTANCE;
    }

    public void initialize() throws Exception {
        Method[] methods = CONTROLLER_CLASS.getMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(RendererMapping.class)) {
                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                RendererMapping templateMapping = method.getAnnotation(RendererMapping.class);

                if (requestMapping == null) continue;

                Class<? extends HtmlRenderer> clazz = templateMapping.name();

                String path = requestMapping.path();
                HtmlRenderer dynamicRenderer = clazz.getDeclaredConstructor().newInstance();

                map.put(path, dynamicRenderer);
            }
        }
    }

    public HtmlRenderer getDynamicTemplate(String path) {
        return map.get(path);
    }

    public boolean contains(String path) {
        return map.containsKey(path);
    }
}
