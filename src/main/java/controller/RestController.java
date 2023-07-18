package controller;

import dto.UserFormRequestDto;
import mapper.ResponseMapper;
import model.HttpRequest;
import model.HttpResponse;
import model.enums.HttpStatusCode;
import model.enums.MIME;
import model.enums.Method;
import service.FileService;
import service.UserService;

import java.io.FileNotFoundException;

import static constant.Uri.INDEX_HTML_URI;
import static constant.Uri.USER_CREATE_URI;
import static util.StringUtils.*;

public class RestController {
    private final FileService fileService;
    private final UserService userService;
    private final ResponseMapper responseMapper;

    public RestController() {
        fileService = new FileService();
        userService = new UserService();
        responseMapper = new ResponseMapper();
    }

    public HttpResponse route(HttpRequest httpRequest) throws FileNotFoundException {
        HttpResponse response = responseMapper.createNotFoundResponse(httpRequest);
        if (isNotRestfulRequest(httpRequest)) {
            response = sendNotRestfulResponse(httpRequest);
        }

        if (httpRequest.match(Method.GET, USER_CREATE_URI)) {
            response = addUserByForm(httpRequest);
        }
        return response;
    }

    private boolean isNotRestfulRequest(HttpRequest httpRequest) {
        return httpRequest.match(Method.GET) && httpRequest.isUriStaticFile();
    }

    private HttpResponse addUserByForm(HttpRequest request) {
        try {
            UserFormRequestDto userFormRequestDto = request.paramsToDto();
            userService.createByForm(userFormRequestDto);
            return responseMapper
                    .createRedirectResponse(request, HttpStatusCode.MOVED_PERMANENTLY, INDEX_HTML_URI);
        } catch (Exception e) {
            return responseMapper.createBadRequestResponse(request);
        }
    }

    private HttpResponse sendNotRestfulResponse(HttpRequest httpRequest) {
        String uri = httpRequest.getUri();
        String[] extension = splitBy(uri, COMMA_MARK);
        int extensionIndex = extension.length - 1;
        MIME mime = MIME.valueOf(extension[extensionIndex].toUpperCase());
        return getHttpResponse(httpRequest, httpRequest.getUri(), mime);
    }

    private HttpResponse getHttpResponse(HttpRequest request, String path, MIME extension) {
        try {
            String fileContents = fileService.openFile(path, extension);
            return responseMapper
                    .createHttpResponse(request, HttpStatusCode.OK, fileContents, extension);
        } catch (FileNotFoundException e) {
            return responseMapper.createBadRequestResponse(request);
        }
    }
}
