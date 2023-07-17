package webserver.myframework.requesthandler;

import webserver.myframework.bean.exception.BeanNotFoundException;
import webserver.myframework.requesthandler.exception.RequestHandlerException;

public interface RequestHandlerInitializer {
    void initialize() throws BeanNotFoundException, RequestHandlerException;
}
