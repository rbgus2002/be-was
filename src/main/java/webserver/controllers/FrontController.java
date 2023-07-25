//package webserver.controllers;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import webserver.http.HttpRequest;
//import webserver.http.HttpResponse;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class FrontController {
//    private static final FrontController FRONT_CONTROLLER = new FrontController();
//    private static final Logger logger = LoggerFactory.getLogger(FrontController.class);
//
//    private static final StaticFileController staticFileController = StaticFileController.getInstance();
//    private static final Map<String, Controller> requestControllers = new HashMap<>() {{
//        // TODO: lazyInitialize? -> lazy하게 관리해주는 클래스 따로 정리?
//        put("/user/create", UserCreateController.getInstance());
//    }};
//
//    public static FrontController getInstance() {
//        return FRONT_CONTROLLER;
//    }
//
//    public HttpResponse resolveRequest(HttpRequest request) {
//        return requestControllers.getOrDefault(request.uri().getPath(), staticFileController).handle(request);
//    }
//}
