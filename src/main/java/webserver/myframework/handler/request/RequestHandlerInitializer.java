package webserver.myframework.handler.request;

import webserver.myframework.bean.exception.BeanNotFoundException;
import webserver.myframework.handler.request.exception.RequestHandlerException;

public interface RequestHandlerInitializer {
    void initialize() throws BeanNotFoundException, RequestHandlerException;
}
