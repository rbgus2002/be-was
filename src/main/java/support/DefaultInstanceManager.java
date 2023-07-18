package support;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static exception.ExceptionName.ALREADY_REGISTERED;

public class DefaultInstanceManager implements InstanceManager {

    private static final DefaultInstanceManager INSTANCE = new DefaultInstanceManager();
    private final Map<Class<?>, Object> instances = new ConcurrentHashMap<>(16);

    private DefaultInstanceManager() {
    }

    public static DefaultInstanceManager getManageObjectFactory() {
        return INSTANCE;
    }

    @Override
    public void addInstance(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if(instances.containsKey(clazz)){
            throw new RuntimeException(ALREADY_REGISTERED);
        }

        Constructor<?> constructor = clazz.getConstructor();
        Object o = constructor.newInstance();
        instances.put(clazz, o);
    }

    @Override
    public Object getInstance(Class<?> clazz) {
        return instances.get(clazz);
    }

}
