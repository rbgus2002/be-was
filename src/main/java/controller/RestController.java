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

import java.io.IOException;
import java.util.Map;

import static constant.Uri.INDEX_HTML_URI;
import static constant.Uri.USER_CREATE_URI;
import static util.StringUtils.COMMA_MARK;
import static util.StringUtils.splitBy;

public class RestController {
    private final FileService fileService;
    private final UserService userService;
    private final ResponseMapper responseMapper;

    public RestController() {
        fileService = new FileService();
        userService = new UserService();
        responseMapper = new ResponseMapper();
    }

    public HttpResponse route(HttpRequest httpRequest) {
        HttpResponse response = responseMapper.createNotFoundResponse(httpRequest);
        try {
            if (isNotRestfulRequest(httpRequest)) {
                response = sendNotRestfulResponse(httpRequest);
            }

            if (httpRequest.match(Method.POST, USER_CREATE_URI)) {
                response = addUserByForm(httpRequest);
            }
            return response;
        } catch (IOException e) {
            return responseMapper.createBadRequestResponse(httpRequest);
        }
    }

    private HttpResponse addUserByForm(HttpRequest request) {
        Map<String, String> bodyMap = request.getBodyMap();
        userService.createByForm(new UserFormRequestDto(bodyMap));
        return responseMapper
                .createRedirectResponse(request, HttpStatusCode.MOVED_PERMANENTLY, INDEX_HTML_URI);
    }

    private HttpResponse sendNotRestfulResponse(HttpRequest httpRequest) throws IOException {
        String uri = httpRequest.getUri();
        String[] extension = splitBy(uri, COMMA_MARK);

        int extensionIndex = extension.length - 1;
        MIME mime = MIME.valueOf(extension[extensionIndex].toUpperCase());
        return getHttpResponse(httpRequest, httpRequest.getUri(), mime);
    }

    private HttpResponse getHttpResponse(HttpRequest request, String path, MIME extension) throws IOException {
        byte[] fileContents = fileService.openFile(path, extension);
        return responseMapper
                .createHttpResponse(request, HttpStatusCode.OK, fileContents, extension);
    }

    private boolean isNotRestfulRequest(HttpRequest httpRequest) {
        return httpRequest.match(Method.GET) && httpRequest.isUriStaticFile();
    }
}
