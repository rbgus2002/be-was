package webserver;

import model.HttpRequest;
import model.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.ModelAndView;
import view.ViewResolver;

public final class DispatcherServlet {

    private static final Logger logger = LoggerFactory.getLogger(ServletContainer.class);

    private final HttpRequest request;
    private final HttpResponse response;
    private HandlerMapping handlerMapping;
    private HandlerAdapter handlerAdapter;
    private ViewResolver viewResolver;

    public DispatcherServlet(HttpRequest request, HttpResponse response) {
        this.request = request;
        this.response = response;

        initStrategies();
    }

    protected void initStrategies() {
        initHandlerMapping();
        initHandlerAdapter();
        initViewResolver();
    }

    private void initViewResolver() {
        viewResolver = new ViewResolver();
    }

    private void initHandlerAdapter() {
        handlerAdapter = new HandlerAdapter();
    }

    private void initHandlerMapping() {
        handlerMapping = new HandlerMapping();
    }

    //TODO: request, response의 초기화 시점
    protected void doService(HttpRequest request, HttpResponse response) {

        doDispatch(request, response);
    }

    //TODO: doDispatch(Request, Response) = 해당되는 컨트롤러(handler) 찾기 => handlerMapping 통해서, 컨트롤러 내부 메서드 실행(invoke)
    protected void doDispatch(HttpRequest request, HttpResponse response) {
        Object handler = handlerMapping.getHandler(request);
        ModelAndView modelAndView = null;

        try {
            modelAndView = handlerAdapter.handle(handler);
        } catch (Exception e) {

        }
    }

    //TODO: modelAndView 결과를 보고 에러면 에러 페이지 출력
    private void processDispatchResult(HttpRequest request, HttpResponse response, ModelAndView modelAndView) {

    }

    //TODO: 에러가 없으면 View 렌더링
    private void render(ModelAndView modelAndView, HttpRequest request, HttpResponse response) {

    }

}
