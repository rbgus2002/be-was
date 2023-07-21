package global.handler;

import controller.Controller;
import global.constant.HttpMethod;
import global.request.RequestLine;

public interface Handler {

    boolean matchHttpMethod(HttpMethod httpMethod);

    String startController(RequestLine requestLine, Controller controller) throws Exception;
}