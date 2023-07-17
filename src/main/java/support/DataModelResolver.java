package support;

import support.annotation.DataModel;
import support.annotation.DataModelField;
import utils.ClassListener;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class DataModelResolver {
    private final static Map<String, DataModelWrapper> dataModelClasses = new HashMap<>();

    static {
        List<Class<?>> modelClasses = ClassListener.scanClass("model");

        modelClasses.forEach(clazz -> {
            DataModel annotation = clazz.getAnnotation(DataModel.class);
            if (annotation != null) {
                String path = annotation.path();
                List<Field> dataModelFields = Arrays.stream(clazz.getDeclaredFields())
                        .filter(field -> field.isAnnotationPresent(DataModelField.class))
                        .collect(Collectors.toUnmodifiableList());
                DataModelWrapper dataModelWrapper = new DataModelWrapper(clazz, dataModelFields);
                dataModelClasses.put(path, dataModelWrapper);
            }
        });
    }

    public static DataModelWrapper resolve(String path) {
        return dataModelClasses.get(path);
    }

}
