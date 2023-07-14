package controller;

import model.HttpRequest;
import model.HttpResponse;
import model.enums.HttpStatusCode;
import model.enums.Method;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RestController {

    public static final String INDEX_HTML_RELATIVE_PATH = "src/main/resources/templates/index.html";
    public static final String INDEX_HTML_URI = "/index.html";
    public static final String PAGE_NOT_FOUND = "요청하신 페이지가 없습니다.";
    public static final String PAGE_BAD_REQUEST = "잘못된 요청입니다.";

    public HttpResponse render(HttpRequest httpRequest) throws FileNotFoundException {
        if (httpRequest.match(Method.GET, INDEX_HTML_URI)) {
            return getBasicHtmlBody(httpRequest);
        }
        return createNotFoundResponse(httpRequest);
    }

    private HttpResponse getBasicHtmlBody(HttpRequest request) {
        try {
            String fileContents = this.getFileIn(INDEX_HTML_RELATIVE_PATH);
            return createHttpResponse(request, HttpStatusCode.OK, fileContents);
        } catch (FileNotFoundException e) {
            return createBadRequestResponse(request);
        }
    }

    private HttpResponse createHttpResponse(HttpRequest request, HttpStatusCode statusCode, String body) {
        return HttpResponse.of(request, statusCode, body);
    }

    private String getFileIn(String relativePath) throws FileNotFoundException {
        String result = "";
        File targetFile = new File(relativePath);
        Scanner fileScanner = new Scanner(targetFile);
        result = fileScanner.useDelimiter("\\Z").next();
        fileScanner.close();
        return result;
    }

    private HttpResponse createNotFoundResponse(HttpRequest httpRequest) {
        return createHttpResponse(httpRequest, HttpStatusCode.NOT_FOUND, PAGE_NOT_FOUND);
    }

    private HttpResponse createBadRequestResponse(HttpRequest httpRequest) {
        return createHttpResponse(httpRequest, HttpStatusCode.BAD_REQUEST, PAGE_BAD_REQUEST);
    }
}
