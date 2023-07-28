package support.instance;

public interface InstanceFactory {

    Object get();

    Class<?> getInstanceType();

}
