package global.handler;

import controller.Controller;
import global.constant.HttpMethod;

public interface Handler {

    boolean matchHttpMethod(HttpMethod httpMethod);

    String startController(String uri, Controller controller) throws Exception;
}