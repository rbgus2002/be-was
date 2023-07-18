package webserver.myframework.bean;

import webserver.myframework.bean.exception.BeanNotFoundException;
import webserver.myframework.bean.exception.DuplicateBeanException;

import java.lang.annotation.Annotation;
import java.util.List;


public interface BeanContainer {
    void register(Class<?> clazz, Object bean) throws DuplicateBeanException;
    Object findBean(Class<?> name) throws BeanNotFoundException;
    List<Class<?>> getBeanClassHasAnnotation(Class<? extends Annotation> annotation);
    List<Class<?>> getAllBeans();
}
