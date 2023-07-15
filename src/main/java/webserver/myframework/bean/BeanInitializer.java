package webserver.myframework.bean;

import webserver.myframework.bean.exception.BeanConstructorException;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

public interface BeanInitializer {
    void initialize(String packageName, BeanContainer beanContainer) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException, BeanConstructorException, ReflectiveOperationException, FileNotFoundException;
}
