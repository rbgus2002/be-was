package view.view;

import webserver.ContentType;
import webserver.response.HttpResponse;
import webserver.response.HttpStatus;

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
    public void render(Map<String, Object> model, DataOutputStream dos) throws IOException {

        byte[] body = Files.readAllBytes(Paths.get(filePath));

        HttpResponse httpResponse = HttpResponse.of(HttpStatus.OK, ContentType.HTML, body);
        httpResponse.sendResponse(dos);
    }
}

