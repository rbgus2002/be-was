package support.instance;

public interface InstanceManager {

    void addInstance(Class<?> clazz);

    <T> T getInstance(Class<T> clazz);

}
