package webserver.view.view;

import exception.internalServerError.FileRenderException;
import webserver.http.Constants.HeaderField;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.http.Constants.HttpStatus;

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
            if(filePath.equals("redirect:")) {
                response.sendResponse(dos);
                return;
            }
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

