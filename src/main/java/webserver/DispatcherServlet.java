package webserver;

import model.HttpRequest;
import model.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.ModelAndView;
import view.View;
import view.ViewResolver;

public final class DispatcherServlet {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private HandlerMapping handlerMapping;
    private HandlerAdapter handlerAdapter;
    private ViewResolver viewResolver;

    public DispatcherServlet() {
        initStrategies();
    }

    private void initStrategies() {
        initHandlerMapping();
        initHandlerAdapter();
        initViewResolver();
    }

    private void initHandlerAdapter() {
        handlerAdapter = new HandlerAdapter();
    }

    private void initHandlerMapping() {
        handlerMapping = new HandlerMapping();
    }

    private void initViewResolver() {
        viewResolver = new ViewResolver();
    }

     void doService(HttpRequest request, HttpResponse response) {

        doDispatch(request, response);
    }

    private void doDispatch(HttpRequest request, HttpResponse response) {
        Class<?> handler = handlerMapping.getHandler(request);

        try {
            ModelAndView modelAndView = handlerAdapter.handle(request, response, handler);
            View view = getViewFromResolver(request, modelAndView);

            view.render(request, response, modelAndView);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private View getViewFromResolver(HttpRequest request, ModelAndView mv) {
        if(mv == null) {
            return viewResolver.getStaticView(request);
        }

        return viewResolver.getDynamicView(mv);
    }
}
