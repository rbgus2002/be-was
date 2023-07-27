package support.instance;

import support.annotation.AutoInject;
import support.annotation.Component;
import support.annotation.ComponentConstructor;
import utils.ClassListener;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class InstanceInitializer {

    private static DefaultInstanceManager defaultInstanceManager;

    public static DefaultInstanceManager getDefaultInstanceManager() {
        if (defaultInstanceManager == null) {
            defaultInstanceManager = DefaultInstanceManager.getDefaultInstanceManger();
        }
        return defaultInstanceManager;
    }

    public static void initializeContainer() {
        List<Class<?>> classes = ClassListener.scanClass("");

        Map<Class<?>, Integer> visited = new HashMap<>();

        List<Class<?>> filteredClasses = classes.stream()
                .filter(clazz -> !clazz.isAnnotation() && hasAnnotation(clazz, Component.class, visited))
                .collect(Collectors.toUnmodifiableList());

        visited.clear();

        filteredClasses.forEach(type -> addInstance(type, filteredClasses));
    }

    private static void addInstance(Class<?> type, List<Class<?>> componentList) {
        if (!componentList.contains(type)) {
            throw new RuntimeException("인스턴스 메니저에서 관리하지 않는 인스턴스입니다.");
        }

        String name = type.getName().replaceFirst(".*\\.", "");
        if ("".equals(name)) {
            name = type.getName();
        }
        try {
            Constructor<?>[] constructors = type.getConstructors();
            Constructor<?> componentConstructor = constructors.length == 1 ? constructors[0] :
                    Arrays.stream(constructors)
                            .filter(constructor -> constructor.isAnnotationPresent(ComponentConstructor.class))
                            .findAny()
                            .orElse(null);

            assert componentConstructor != null;
            Parameter[] parameters = componentConstructor.getParameters();
            Object[] array = Arrays.stream(parameters)
                    .filter(parameter -> parameter.isAnnotationPresent(AutoInject.class))
                    .map(parameter -> {
                                Object instance = getDefaultInstanceManager().getInstance(
                                        "".equals(parameter.getAnnotation(AutoInject.class).name()) ? parameter.getType().getName().replaceFirst(".*\\.", "") : parameter.getAnnotation(AutoInject.class).name(),
                                        parameter.getType()
                                );
                                if (instance == null) {
                                    addInstance(parameter.getType(), componentList);
                                }
                                return instance;
                            }
                    ).toArray();

            getDefaultInstanceManager().addInstance(name, componentConstructor.newInstance(array));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean hasAnnotation(Class<?> clazz, Class<? extends Annotation> annotationClass, Map<Class<?>, Integer> visited) {
        if (visited.get(clazz) != null && visited.get(clazz) == 0) {
            return false;
        }
        visited.put(clazz, 0);

        if (clazz.isAnnotationPresent(annotationClass) || visited.get(clazz) == 1) {
            visited.compute(clazz, (k, v) -> 1);
            return true;
        }

        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {
            if (hasAnnotation(annotation.annotationType(), annotationClass, visited)) {
                visited.compute(clazz, (k, v) -> 1);
                return true;
            }
        }

        return false;
    }

}
