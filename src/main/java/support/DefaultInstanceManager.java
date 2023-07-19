package support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import static exception.ExceptionName.ALREADY_REGISTERED;

public class DefaultInstanceManager implements InstanceManager {

    private static final DefaultInstanceManager INSTANCE = new DefaultInstanceManager();
    private final Map<Class<?>, Object> instances = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(DefaultInstanceManager.class);

    private DefaultInstanceManager() {
    }

    public static DefaultInstanceManager getInstanceMagager() {
        return INSTANCE;
    }

    @Override
    public void addInstance(Class<?> clazz) {
        if (instances.containsKey(clazz)) {
            throw new RuntimeException(ALREADY_REGISTERED);
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
    public Object getInstance(Class<?> clazz) {
        return instances.get(clazz);
    }

}
