package controller;

import dto.UserFormRequestDto;
import model.HttpRequest;
import model.HttpResponse;
import model.enums.HttpStatusCode;
import model.enums.Method;
import service.FileService;
import service.ResponseService;
import service.UserService;

import java.io.FileNotFoundException;

import static constant.Uri.USER_CREATE_URI;
import static util.StringUtils.NO_CONTENT;

public class RestController {
    private final FileService fileService;
    private final UserService userService;
    private final ResponseService responseService;

    public RestController() {
        fileService = new FileService();
        userService = new UserService();
        responseService = new ResponseService();
    }

    public HttpResponse route(HttpRequest httpRequest) throws FileNotFoundException {
        HttpResponse response = responseService.createNotFoundResponse(httpRequest);
        if (httpRequest.match(Method.GET) && httpRequest.endsWithHtml()) {
            response = sendNotRestfulResponse(httpRequest);
        }

        if (httpRequest.match(Method.GET, USER_CREATE_URI)) {
            response = addUserByForm(httpRequest);
        }
        return response;
    }

    private HttpResponse addUserByForm(HttpRequest request) {
        try {
            UserFormRequestDto userFormRequestDto = request.paramsToDto();
            userService.createByForm(userFormRequestDto);
            return responseService
                    .createHttpResponse(request, HttpStatusCode.CREATED, NO_CONTENT);
        } catch (Exception e) {
            return responseService.createBadRequestResponse(request);
        }
    }

    private HttpResponse sendNotRestfulResponse(HttpRequest request) {
        return getHttpResponse(request, request.getUri());
    }

    private HttpResponse getHttpResponse(HttpRequest request, String path) {
        try {
            String fileContents = fileService.openFile(path);
            return responseService
                    .createHttpResponse(request, HttpStatusCode.OK, fileContents);
        } catch (FileNotFoundException e) {
            return responseService.createBadRequestResponse(request);
        }
    }
}
