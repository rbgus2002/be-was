package support.instance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class DefaultInstanceManager implements InstanceManager {

    private static final DefaultInstanceManager INSTANCE = new DefaultInstanceManager();
    private final Map<Class<?>, Object> instances = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(DefaultInstanceManager.class);

    private DefaultInstanceManager() {
    }

    public static DefaultInstanceManager getInstanceManager() {
        return INSTANCE;
    }

    public void clear() {
        instances.clear();
    }

    @Override
    public void addInstance(Class<?> clazz) {
        if (instances.containsKey(clazz)) {
            logger.debug("이미 생성된 인스턴스입니다. {}", clazz.getName());
            return;
        }

        try {
            Constructor<?> constructor = clazz.getConstructor();
            instances.put(clazz, constructor.newInstance());
            logger.debug("새로운 인스턴스가 등록되었습니다. : {}", clazz.getName());
        } catch (Exception e) {
            logger.error("객체 인스턴스 생성에 실패하였습니다. : {}", clazz.getName());
        }
    }

    @Override
    public <T> T getInstance(Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        return clazz.cast(instances.get(clazz));
    }

}
