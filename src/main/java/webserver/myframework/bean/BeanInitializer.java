package webserver.myframework.bean;

import webserver.myframework.bean.exception.BeanConstructorException;

import java.io.FileNotFoundException;

public interface BeanInitializer {
    void initialize(String packageName) throws BeanConstructorException, ReflectiveOperationException, FileNotFoundException;
}
