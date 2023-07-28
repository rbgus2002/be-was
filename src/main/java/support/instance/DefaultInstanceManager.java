package support.instance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultInstanceManager implements InstanceManager {

    private static DefaultInstanceManager INSTANCE = new DefaultInstanceManager();
    private final Map<String, Object> singletonInstances = new HashMap<>();
    private final Map<String, InstanceFactory> lazySetUpInstances = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(DefaultInstanceManager.class);

    public static InstanceManager getInstanceManager() {
        return INSTANCE;
    }

    protected static DefaultInstanceManager getDefaultInstanceManger() {
        return INSTANCE;
    }

    public void addInstance(String name, Object object) {
        if (isContainInstance(name)) {
            logger.debug("이미 등록된 인스턴스입니다. {}", name);
            return;
        }

        try {
            singletonInstances.put(name, object);
            logger.debug("새로운 인스턴스가 등록되었습니다. : {}", name);
        } catch (Exception e) {
            logger.error("객체 인스턴스 생성에 실패하였습니다. : {}\n message: {}\n {}", name, e.getMessage(), e.getStackTrace());
        }
    }

    public void addInstanceWithLazySetUp(String name, InstanceFactory objectFactory) {
        if (lazySetUpInstances.containsKey(name)) {
            logger.debug("이미 등록된 인스턴스 공장이 존재합니다. {}", objectFactory.getClass().getName());
            return;
        }

        lazySetUpInstances.put(name, objectFactory);
    }

    @Override
    public boolean isContainInstance(String name) {
        return singletonInstances.containsKey(name) || lazySetUpInstances.containsKey(name);
    }

    @Override
    public Object getInstance(String name) {
        Object instance = singletonInstances.get(name);
        if (instance != null) {
            return instance;
        }

        InstanceFactory instanceFactory = lazySetUpInstances.get(name);
        if (instanceFactory != null) {
            return lazySetUp(name, instanceFactory);
        }

        return null;
    }

    @Override
    public <T> T getInstance(String name, Class<T> type) {
        return type.cast(getInstance(name));
    }

    @Override
    public <T> List<T> getInstances(Class<T> type) {
        List<T> instances = new ArrayList<>();
        singletonInstances.forEach(
                (name, instance) -> {
                    if (instance.getClass() == type) {
                        instances.add(type.cast(instance));
                    }
                });

        lazySetUpInstances.forEach((name, factory) -> {
            if (factory.getInstanceType() == type) {
                instances.add(type.cast(lazySetUp(name, factory)));
            }
        });

        return instances;
    }

    private Object lazySetUp(String name, InstanceFactory instanceFactory) {
        Object newInstance = instanceFactory.get();
        addInstance(name, newInstance);
        lazySetUpInstances.remove(name);
        return newInstance;
    }

}
