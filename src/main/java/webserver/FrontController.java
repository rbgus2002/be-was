package webserver;

import controller.HttpController;
import controller.StaticController;
import http.HttpRequest;
import http.HttpResponse;
import http.Utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class FrontController {
    private final Map<String, HttpController> controllerMap;
    private final HttpController staticController;

    public static FrontController getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final FrontController INSTANCE = new FrontController();
    }

    private FrontController() {
        controllerMap = ControllerScanner.scan();
        staticController = new StaticController();
    }

    public void service(DataOutputStream dos, HttpRequest request, HttpResponse response) throws IOException {
        String url = request.getUrl();
        HttpController controller = controllerMap.getOrDefault(url, staticController);
        String viewName = controller.process(request, response);
        viewResolve(viewName, response);
        render(dos, response);
    }

    private void viewResolve(String viewName, HttpResponse response) throws IOException {
        if (viewName == null) {
            return;
        }
        if (viewName.startsWith("redirect:")) {
            String url = viewName.substring("redirect:".length());
            response.setRedirect(url);
            return;
        }
        Path path = Paths.get("src/main/resources/static" + viewName);
        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            response.setNotFound();
            return;
        }
        byte[] body = Files.readAllBytes(path);
        String type = Utils.getMimeType(path);
        response.setBody(body);
        response.setContentType(type);
    }

    private void render(DataOutputStream dos, HttpResponse response) throws IOException {
        dos.writeBytes(response.getVersion() + " " + response.getMethod() + " " + response.getStatusMessage() + "\r\n");
        Map<String, String> headers = response.getHeaders();
        for (Map.Entry<String, String> header : headers.entrySet()) {
            dos.writeBytes(header.getKey() + ": " + header.getValue() + "\r\n");
        }
        dos.writeBytes("\r\n");
        dos.write(response.getBody(), 0, response.getBody().length);
        dos.flush();
    }

}
