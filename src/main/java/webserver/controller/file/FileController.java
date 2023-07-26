package webserver.controller.file;

import webserver.controller.Controller;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.utils.ContentTypeResolver;
import webserver.utils.FileUtils;
import webserver.utils.HttpField;

import java.io.IOException;

public class FileController implements Controller {
    @Override
    public void process(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        String filePath = httpRequest.getField(HttpField.PATH);
        String responsePage = FileUtils.checkFilePath(filePath);
        setResponseWithFileData(httpResponse, responsePage);
    }

    private void setResponseWithFileData(HttpResponse httpResponse, String filePath) throws IOException {
        byte[] fileData = FileUtils.readFileBytes(filePath);
        HttpStatus status = resolveHttpStatus(filePath);
        String contentType = ContentTypeResolver.getContentType(filePath);

        httpResponse.setStatus(status);
        httpResponse.set(HttpField.CONTENT_TYPE, contentType);
        httpResponse.set(HttpField.CONTENT_LENGTH, fileData.length);
        httpResponse.setBody(fileData);
    }

    private HttpStatus resolveHttpStatus(String path) {
        if (path.equals("/404.html")) {
            return HttpStatus.NOT_FOUND;
        }
        return HttpStatus.OK;
    }
}
