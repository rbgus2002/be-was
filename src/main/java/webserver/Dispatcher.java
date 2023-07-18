package webserver;

import common.HttpRequest;
import common.HttpResponse;
import common.enums.ContentType;
import common.enums.Method;
import controller.Controller;
import modelview.ModelView;
import view.View;

public class Dispatcher {
    public void dispatch(HttpRequest request, HttpResponse response) {
        // HTTP 요청을 처리할 컨트롤러 가져오기
        Controller controller = fetchController(request);

        // 컨트롤러가 요청을 처리
        ModelView modelView = controller.process(request);

        // 논리적 경로 -> 물리적 경로
        View view = ViewResolver.resolve(request.getContentType(), modelView.getViewName());

        // 뷰 렌더링
        view.render(modelView.getModel(), request, response);
    }

    private Controller fetchController(HttpRequest request) {
        RequestControllerMapper mapper = RequestControllerMapper.getInstance();

        String path = request.getPath();
        Method method = request.getMethod();
        ContentType contentType = request.getContentType();

        Controller controller;
        if (contentType.isStaticContent()) {
            controller = mapper.get("/static", method);
        }
        else if (!mapper.contains(path, method)) {
            controller = mapper.get("/error", Method.GET);
        }
        else {
            controller = mapper.get(path, method);
        }
        return controller;
    }
}