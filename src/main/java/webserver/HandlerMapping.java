package webserver;

import model.HttpRequest;

public class HandlerMapping {

    //TODO: getHandler(HttpRequest) 각 컨트롤러의 메서드를 검사해 일치하는 컨트롤러 찾기
    protected Object getHandler(HttpRequest request) {
        String uri = request.getRequestURI();

        return null;
    }
}
