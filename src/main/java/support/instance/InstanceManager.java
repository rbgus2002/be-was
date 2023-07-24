package support.instance;

public interface InstanceManager {

    void addInstance(Class<?> clazz);

    Object getInstance(Class<?> clazz);

}
