package support;

import support.annotation.Container;
import utils.ClassListener;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Collectors;

public abstract class InstanceInitializer {

    public static void initializeContainer() {
        List<Class<?>> classes = ClassListener.scanClass("");

        List<Class<?>> filteredClasses = classes.stream()
                .filter(clazz -> !clazz.isAnnotation() && hasAnnotation(clazz, Container.class))
                .collect(Collectors.toList());

        DefaultInstanceManager manageObjectFactory = DefaultInstanceManager.getInstanceMagager();

        filteredClasses.forEach(manageObjectFactory::addInstance);
    }

    private static boolean hasAnnotation(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        if (clazz.isAnnotationPresent(annotationClass)) {
            return true;
        }

        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {
            if (hasAnnotation(annotation.annotationType(), annotationClass)) {
                return true;
            }
        }

        return false;
    }

}
