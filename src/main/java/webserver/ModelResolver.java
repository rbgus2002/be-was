package webserver;

import annotation.DataModel;
import utils.ClassListener;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class ModelResolver {
    private final static List<Class<?>> dataModelClasses = new ArrayList<>();

    static {
        List<Class<?>> modelClasses = ClassListener.scanClass("model");

        modelClasses.forEach(clazz -> {
            Annotation annotation = clazz.getAnnotation(DataModel.class);
            if (annotation != null) {
                dataModelClasses.add(clazz);
            }
        });
    }

    public static Optional<Class<?>> resolve(String path) throws ClassNotFoundException {
        return dataModelClasses.stream()
                .filter(dataModelClass -> dataModelClass.getAnnotation(DataModel.class).path().equals(path))
                .findAny();
    }
}
