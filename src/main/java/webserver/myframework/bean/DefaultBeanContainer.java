package webserver.myframework.bean;

import webserver.myframework.bean.exception.BeanNotFoundException;
import webserver.myframework.bean.exception.DuplicateBeanException;
import webserver.myframework.utils.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DefaultBeanContainer implements BeanContainer {
    private final Map<Class<?>, Object> beanMap = new HashMap<>();

    @Override
    public void addBean(Class<?> clazz, Object bean) throws DuplicateBeanException {
        Object resultBean = beanMap.get(clazz);
        if(resultBean != null) {
            throw new DuplicateBeanException();
        }
        beanMap.put(clazz, bean);
    }

    @Override
    public Object findBean(Class<?> clazz) throws BeanNotFoundException {
        Object bean = beanMap.get(clazz);
        if(bean != null) {
            return bean;
        }
        for (Object curBean : beanMap.values()) {
            if(clazz.isAssignableFrom(curBean.getClass())) {
                return curBean;
            }
        }
        throw new BeanNotFoundException();
    }

    @Override
    public List<Class<?>> getBeanClassHasAnnotation(Class<? extends Annotation> annotation) {
        return beanMap.keySet().stream()
                .filter(clazz -> ReflectionUtils.isClassHasAnnotation(clazz, annotation))
                .collect(Collectors.toList());
    }
}
