package support;

public interface InstanceManager {

    void addInstance(Class<?> clazz);

    Object getInstance(Class<?> clazz);

}
