package support;

import support.annotation.Container;
import utils.ClassListener;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class InstanceInitializer {

    public static void initializeContainer() {
        List<Class<?>> classes = ClassListener.scanClass("");

        Set<Class<?>> isVisited = new HashSet<>();

        List<Class<?>> filteredClasses = classes.stream()
                .filter(clazz -> !clazz.isAnnotation() && hasAnnotation(clazz, Container.class, isVisited))
                .collect(Collectors.toList());

        isVisited.clear();

        DefaultInstanceManager manageObjectFactory = DefaultInstanceManager.getInstanceMagager();

        filteredClasses.forEach(manageObjectFactory::addInstance);
    }

    private static boolean hasAnnotation(Class<?> clazz, Class<? extends Annotation> annotationClass, Set<Class<?>> isVisited) {
        if(isVisited.contains(clazz)){
            return false;
        }
        isVisited.add(clazz);

        if (clazz.isAnnotationPresent(annotationClass)) {
            return true;
        }

        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {
            if (hasAnnotation(annotation.annotationType(), annotationClass, isVisited)) {
                return true;
            }
        }

        return false;
    }

}
