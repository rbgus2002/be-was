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

    public static void service(HttpRequest request, HttpResponse response) throws IOException {
        if(StaticFIleUtils.isExistedStaticFileRequest(request.getUrl())) {
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
