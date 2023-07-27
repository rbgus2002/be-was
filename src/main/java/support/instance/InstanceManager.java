package support.instance;

import java.util.List;

public interface InstanceManager {

    boolean isContainInstance(String name);

    Object getInstance(String name);

    <T> T getInstance(String name, Class<T> type);

    <T> List<T> getInstances(Class<T> type);

}
