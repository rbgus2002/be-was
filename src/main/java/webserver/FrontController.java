package webserver;

import controller.Controller;
import utils.StaticFIleUtils;
import webserver.exception.BadRequestException;
import webserver.exception.ConflictException;
import webserver.exception.NotFoundException;
import webserver.reponse.HttpResponse;
import webserver.reponse.HttpResponseStatus;
import webserver.reponse.Type;
import webserver.request.HttpRequest;

import java.io.IOException;

public class FrontController {

    public static void service(HttpRequest request, HttpResponse response) throws IOException {
        try {
            Controller controller = HttpRequestMapper.getInstance().getController(request.getMethod(), request.getUrl());

            if(controller != null){
                controller.execute(request, response);
                return;
            }

            if(StaticFIleUtils.isExistedStaticFileRequest(request.getUrl())) {
                StaticFIleUtils.getStaticByte(request.getUrl(), response);
                response.setStatus(HttpResponseStatus.STATUS_200);
                return;
            }

            throw new NotFoundException("요청과 일치하는 페이지를 찾을 수 없습니다!");

        } catch (NotFoundException e) {
            response.setStatus(HttpResponseStatus.STATUS_404);
            response.setBodyByText(e.getMessage());
        } catch (BadRequestException e) {
            response.setStatus(HttpResponseStatus.STATUS_400);
            response.setBodyByText(e.getMessage());
        } catch (ConflictException e) {
            response.setStatus(HttpResponseStatus.STATUS_409);
            response.setBodyByText(e.getMessage());
        }
    }
}
