package webserver.view.view;

import exception.internalServerError.FileRenderException;
import webserver.Constants.HeaderField;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.Constants.HttpStatus;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class StaticView implements View {

    private final String filePath;

    public StaticView(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void render(final HttpRequest request, final HttpResponse response, final Map<String, Object> model, final DataOutputStream dos) throws FileRenderException {

        try {
            byte[] body = Files.readAllBytes(Paths.get(filePath));

            response.setHttpStatus(HttpStatus.OK);
            response.addHeaderElement(HeaderField.contentType, request.getContentType().getDescription());
            response.setBody(body);
            response.sendResponse(dos);
        } catch (IOException e) {
            throw new FileRenderException(filePath);
        }
    }
}

