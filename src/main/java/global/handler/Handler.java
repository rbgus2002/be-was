package global.handler;

import controller.Controller;
import global.constant.HttpMethod;
import global.request.RequestLine;

public interface Handler {

    boolean matchHttpMethod(HttpMethod httpMethod);

    byte[] startController(RequestLine requestLine, Controller controller) throws Exception;
}