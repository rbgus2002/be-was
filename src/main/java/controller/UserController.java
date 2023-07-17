package controller;

import annotation.Controller;
import annotation.RequestMapping;
import annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.DataModelResolver;
import support.DataModelWrapper;
import support.HttpMethod;
import webserver.RequestHandler;

import java.lang.reflect.InvocationTargetException;

@Controller(value = "/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    @RequestMapping(method = HttpMethod.GET, value = "/create")
    public void create(@RequestParam String userId, @RequestParam String password, @RequestParam String name, @RequestParam String email) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {

        logger.debug("create 실행");
        System.out.println("create 실행");

        DataModelWrapper resolve = DataModelResolver.resolve("/user/create");

        if (resolve != null) {
            // TODO: 데이터 처리 기능 추가
//            Query requestQuery = requestHeader.getRequestQuery();
//            Object dataModel = resolve.constructClass(requestQuery);
        }
    }

}
