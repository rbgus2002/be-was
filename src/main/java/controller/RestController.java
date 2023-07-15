package controller;

import model.HttpRequest;
import model.HttpResponse;
import model.enums.HttpStatusCode;
import model.enums.Method;
import service.FileService;
import service.ResponseService;

import java.io.FileNotFoundException;

import static constant.SourcePath.BASIC_INDEX_PATH;
import static constant.SourcePath.USER_FORM_PATH;
import static constant.Uri.BASIC_INDEX_URI;
import static constant.Uri.USER_FORM_URI;

public class RestController {
    private final FileService fileService;
    private final ResponseService responseService;

    public RestController() {
        fileService = new FileService();
        responseService = new ResponseService();
    }

    public HttpResponse route(HttpRequest httpRequest) throws FileNotFoundException {
        HttpResponse response = responseService.createNotFoundResponse(httpRequest);
        if (httpRequest.match(Method.GET, BASIC_INDEX_URI)) {
            // TODO *.html static 한 것들은 그냥 넘겨주게
            response = getHttpResponse(httpRequest, BASIC_INDEX_PATH);
        }
        // TODO
        if (httpRequest.match(Method.GET, USER_FORM_URI)) {
            response = getHttpResponse(httpRequest, USER_FORM_PATH);
        }
        return response;
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
