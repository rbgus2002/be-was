package webserver;

import controller.Controller;
import utils.StaticFIleUtils;
import webserver.exception.NotFoundException;
import webserver.reponse.HttpResponse;
import webserver.reponse.HttpResponseStatus;
import webserver.reponse.Type;
import webserver.request.HttpRequest;

import java.io.IOException;

public class FrontController {
    //TODO 정적파일 요청인지 api 요청인지 확인 후 그에 따른 로직 처리

    public static void service(HttpRequest request, HttpResponse response) throws IOException {
        if(StaticFIleUtils.isExistedStaticFileRequest(request.getUrl())) {
            //TODO response에 정적 파일 제공, content-type 제공
            StaticFIleUtils.getStaticByte(request.getUrl(), response);
            return;
        }

        Controller controller = HttpRequestMapper.getInstance().getController(request.getMethod(), request.getUrl());

        if(controller == null){
            response.setStatus(HttpResponseStatus.STATUS_404);
            throw new NotFoundException();
        }

        controller.verifyRequest(request);
        controller.execute(request);
        controller.manageResponse(response);
    }
}
