package support;

import java.lang.reflect.InvocationTargetException;

public interface InstanceManager {

    void addInstance(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    Object getInstance(Class<?> clazz);

}
