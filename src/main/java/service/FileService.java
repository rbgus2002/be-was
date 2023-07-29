package service;

import model.User;
import view.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileService {
    private static final List<String> PATH_LIST = new ArrayList<>();
    private static final Map<String, Class> AUTH_PATH_MAP = new HashMap<>();

    private FileService() {
    }

    static {
        PATH_LIST.add("src/main/resources/templates");
        PATH_LIST.add("src/main/resources/static");

        AUTH_PATH_MAP.put("/index.html", IndexHtmlRenderer.class);
        AUTH_PATH_MAP.put("/user/form.html", UserFormHtmlRenderer.class);
        AUTH_PATH_MAP.put("/user/list.html", UserListHtmlRenderer.class);
        AUTH_PATH_MAP.put("/user/login.html", UserLoginHtmlRenderer.class);
        AUTH_PATH_MAP.put("/user/login_failed.html", UserLoginFailedHtmlRenderer.class);
        AUTH_PATH_MAP.put("/user/profile.html", UserProfileHtmlRenderer.class);
    }

    public static byte[] getTargetResource(String path, User sessionUser) throws IOException, InstantiationException, IllegalAccessException {
        for (String rootPath : PATH_LIST) {
            File file = new File(rootPath + path);
            if (file.exists() && file.isFile()) {
                if (AUTH_PATH_MAP.containsKey(path)) {
                    HtmlRenderer htmlRenderer = (HtmlRenderer) AUTH_PATH_MAP.get(path).newInstance();
                    return htmlRenderer.render(sessionUser);
                }
                return Files.readAllBytes(file.toPath());
            }
        }
        throw new NoSuchFileException(path);
    }

    public static byte[] getTargetResource(String path) throws IOException {
        for (String rootPath : PATH_LIST) {
            File file = new File(rootPath + path);
            if (file.exists() && file.isFile()) {
                return Files.readAllBytes(file.toPath());
            }
        }
        throw new NoSuchFileException(path);
    }
}
