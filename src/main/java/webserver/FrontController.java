package webserver;

import controller.DefaultController;
import controller.HomeController;
import controller.HttpController;
import controller.JoinController;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static controller.DefaultController.DEFAULT_CONTROLLER;
import static controller.HomeController.HOME_CONTROLLER;
import static controller.JoinController.JOIN_CONTROLLER;

public class FrontController {
    Map<String, HttpController> controllerMap = new HashMap<>();

    public FrontController() {
        controllerMap.put("/", HOME_CONTROLLER);
        controllerMap.put("/user/create", JOIN_CONTROLLER);
    }

    public void service(DataOutputStream dos, HttpRequest request, HttpResponse response) throws IOException {
        String url = request.getUrl();
        HttpController controller = controllerMap.getOrDefault(url, DEFAULT_CONTROLLER);
        String viewName = controller.process(request, response);
        viewResolve(viewName, response);
        render(dos, response);
    }

    private void viewResolve(String viewName, HttpResponse response) throws IOException {
        if (viewName == null) {
            return;
        }
        Path path;
        path = Paths.get("src/main/resources/static" + viewName);
        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            path = Paths.get("src/main/resources/templates" + viewName);
        }
        byte[] body = Files.readAllBytes(path);
        String filename = path.getFileName().toString();
        String extension = filename.substring(filename.lastIndexOf(".") + 1);
        response.setBody(body);
        response.setContentType(extension);
    }

    private void render(DataOutputStream dos, HttpResponse response) throws IOException {
        dos.writeBytes(response.getVersion() + " " + response.getMethod() + response.getStatusMessage() + "\r\n");
        Map<String, String> headers = response.getHeaders();
        for (Map.Entry<String, String> header : headers.entrySet()) {
            dos.writeBytes(header.getKey() + ": " + header.getValue() + "\r\n");
        }
        dos.writeBytes("\r\n");
        dos.write(response.getBody(), 0, response.getBody().length);
        dos.flush();
    }

}
