package support;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static exception.ExceptionName.ALREADY_REGISTERED;

public class DefaultManageObjectFactory implements ManageObjectFactory {

    private static final DefaultManageObjectFactory INSTANCE = new DefaultManageObjectFactory();

    private final Map<Class<?>, Object> instances = new ConcurrentHashMap<>(16);

    private DefaultManageObjectFactory() {

    }

    public static DefaultManageObjectFactory getManageObjectFactory() {
        return INSTANCE;
    }

    @Override
    public void addInstance(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if(instances.containsKey(clazz)){
            throw new RuntimeException(ALREADY_REGISTERED);
        }

        System.out.println(clazz.getConstructors().length);

        Constructor<?> constructor = clazz.getConstructor();
        Object o = constructor.newInstance();
        instances.put(clazz, o);
    }

    @Override
    public void addInstance(Class<?> clazz, Object args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if(instances.containsKey(clazz)){
            throw new RuntimeException(ALREADY_REGISTERED);
        }

        Constructor<?> constructor = clazz.getConstructor(args.getClass());
        Object o = constructor.newInstance(args);
        instances.put(clazz, o);
    }

    @Override
    public Object getInstance(Class<?> clazz) {
        return instances.get(clazz);
    }
}
