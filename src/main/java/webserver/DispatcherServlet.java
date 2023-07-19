package webserver;

import model.HttpRequest;
import model.HttpResponse;

public class DispatcherServlet {

    public DispatcherServlet(HttpRequest request, HttpResponse response) {

    }

    //TODO: init() = 핸들러매핑 map을 처음 생성해야함

    //TODO: doService(Request, Response) = request에 내용 담고, doDispatch()의 결과를 response에 담기

    //TODO: doDispatch(Request, Response) = 해당되는 컨트롤러(handler) 찾기 => handlerMapping 통해서, 컨트롤러 내부 메서드 실행(invoke)


}
