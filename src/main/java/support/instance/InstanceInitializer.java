package support.instance;

import support.annotation.Container;
import utils.ClassListener;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class InstanceInitializer {

    public static void initializeContainer() {
        List<Class<?>> classes = ClassListener.scanClass("");

        Map<Class<?>, Integer> visited = new HashMap<>();

        List<Class<?>> filteredClasses = classes.stream()
                .filter(clazz -> !clazz.isAnnotation() && hasAnnotation(clazz, Container.class, visited))
                .collect(Collectors.toList());

        visited.clear();

        DefaultInstanceManager manageObjectFactory = DefaultInstanceManager.getInstanceMagager();

        filteredClasses.forEach(manageObjectFactory::addInstance);
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
