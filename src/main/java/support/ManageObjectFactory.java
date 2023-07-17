package support;

import java.lang.reflect.InvocationTargetException;

public interface ManageObjectFactory {

    void addInstance(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    Object getInstance(Class<?> clazz);

}
