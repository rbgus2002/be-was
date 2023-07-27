package webserver;

import model.HttpRequest;
import model.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.ModelAndView;
import view.View;
import view.ViewResolver;

import java.io.IOException;

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

         try {
             doDispatch(request, response);
         } catch (IOException e) {
             logger.error("404 페이지가 존재하지 않습니다.");
         }
     }

    private void doDispatch(HttpRequest request, HttpResponse response) throws IOException {
        Class<?> handler = handlerMapping.getHandler(request);

        View view;
        ModelAndView modelAndView = null;

        try {
            modelAndView = handlerAdapter.handle(request, response, handler);
            view = getViewFromResolver(request, modelAndView);

        } catch (Exception e) {
            view = getViewFromResolver(request, null);
        }

        view.render(request, response, modelAndView);
    }

    private View getViewFromResolver(HttpRequest request, ModelAndView mv) {
        if(mv == null) {
            View view =  viewResolver.getStaticView(request);
            return view;
        }

        View view = viewResolver.getDynamicView(mv);
        return view;
    }
}
