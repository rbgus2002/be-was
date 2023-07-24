package webserver;

import model.HttpRequest;
import model.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.ModelAndView;

public final class DispatcherServlet {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private final HttpRequest request;
    private final HttpResponse response;
    private HandlerMapping handlerMapping;
    private HandlerAdapter handlerAdapter;

    public DispatcherServlet(HttpRequest request, HttpResponse response) {
        this.request = request;
        this.response = response;

        initStrategies();
    }

    private void initStrategies() {
        initHandlerMapping();
        initHandlerAdapter();
    }

    private void initHandlerAdapter() {
        handlerAdapter = new HandlerAdapter();
    }

    private void initHandlerMapping() {
        handlerMapping = new HandlerMapping();
    }

     void doService(HttpRequest request, HttpResponse response) {

        doDispatch(request, response);
    }

    private void doDispatch(HttpRequest request, HttpResponse response) {
        Class<?> handler = handlerMapping.getHandler(request);

        try {
            ModelAndView modelAndView = null;

            //URI와 매핑되는 Controller가 존재하지 않거나 Controller 클래스 자체가 존재하지 않을 때
            if(handler == null) {
                modelAndView = handlerAdapter.staticView(request);
                modelAndView.setResponse(request, response);
                return;
            }

            modelAndView = handlerAdapter.handle(request, response, handler);
            modelAndView.setResponse(request, response);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
