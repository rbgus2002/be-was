package webserver;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class DataModelWrapper {
    private final Class<?> dataModelClass;

    private final List<Field> dataModelClassFields;

    public DataModelWrapper(Class<?> dataModelClass, List<Field> dataModelClassFields) {
        this.dataModelClass = dataModelClass;
        this.dataModelClassFields = dataModelClassFields;
    }

    public Object constructClass(Query query) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Object dataModel = dataModelClass.getConstructor().newInstance();

        for (Field field : dataModelClassFields) {
            field.setAccessible(true);
            field.set(dataModel, query.getValue(field.getName()));
            System.out.println(field.getName());
        }

        return dataModel;
    }

    public boolean equalsClass(Class<?> clazz) {
        return this.dataModelClass == clazz;
    }
}
