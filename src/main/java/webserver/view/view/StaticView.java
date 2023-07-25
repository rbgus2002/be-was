package webserver.view.view;

import exception.internalServerError.FileRenderException;
import webserver.Constants.ContentType;
import webserver.Constants.HttpVersion;
import webserver.request.RequestPath;
import webserver.response.HttpResponse;
import webserver.Constants.HttpStatus;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class StaticView implements View {

    private String filePath;

    public StaticView(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void render(final HttpVersion version, final ContentType contentType, final Map<String, Object> model, final DataOutputStream dos) throws FileRenderException {

        try {
            byte[] body = Files.readAllBytes(Paths.get(filePath));

            HttpResponse httpResponse = HttpResponse.ofWithBodyData(version, HttpStatus.OK, contentType, body);
            httpResponse.sendResponse(dos);
        } catch (IOException e) {
            throw new FileRenderException(filePath);
        }
    }
}

