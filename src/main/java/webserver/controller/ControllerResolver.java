package webserver.controller;

import webserver.controller.file.FileController;
import webserver.controller.user.UserListController;
import webserver.controller.user.UserLoginController;
import webserver.controller.user.UserSaveController;
import webserver.utils.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class ControllerResolver {
    private static ControllerResolver instance;

    private final Map<ControllerSignature, Controller> controllers = new HashMap<>();
    private final Controller indexPageController = new IndexPageController();
    private final Controller fileController = new FileController();

    private ControllerResolver() {
        initControllers();
    }

    public static ControllerResolver getInstance() {
        if (instance == null) {
            synchronized (ControllerResolver.class) {
                instance = new ControllerResolver();
            }
        }
        return instance;
    }

    private void initControllers() {
        controllers.put(new ControllerSignature("/", HttpMethod.GET), indexPageController);
        controllers.put(new ControllerSignature("/index.html", HttpMethod.GET), indexPageController);
        controllers.put(new ControllerSignature("/user/create", HttpMethod.POST), new UserSaveController());
        controllers.put(new ControllerSignature("/user/login", HttpMethod.POST), new UserLoginController());
        controllers.put(new ControllerSignature("/user/list", HttpMethod.GET), new UserListController());
    }

    public Controller resolve(String path, String method) {
        return controllers.keySet().stream()
                .filter(signature -> signature.verifySignature(path, method))
                .findFirst()
                .map(controllers::get)
                .orElse(fileController);
    }

    private static class ControllerSignature {
        private final String path;
        private final String method;

        public ControllerSignature(String path, String method) {
            this.path = path;
            this.method = method;
        }

        public boolean verifySignature(String path, String method) {
            if (path == null || method == null) {
                return false;
            }
            return (path.equals(this.path)) && (method.equals(this.method));
        }
    }
}
