package service;

import model.User;
import service.html.*;

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

        AUTH_PATH_MAP.put("/index.html", IndexHtmlService.class);
        AUTH_PATH_MAP.put("/user/form.html", UserFormHtmlService.class);
        AUTH_PATH_MAP.put("/user/list.html", UserListHtmlService.class);
        AUTH_PATH_MAP.put("/user/login.html", UserLoginHtmlService.class);
        AUTH_PATH_MAP.put("/user/login_failed.html", UserLoginFailedHtmlService.class);
    }

    public static byte[] getStaticResource(String path, User sessionUser) throws IOException, InstantiationException, IllegalAccessException {
        for (String rootPath : PATH_LIST) {
            File file = new File(rootPath + path);
            if (file.exists() && file.isFile()) {
                if (AUTH_PATH_MAP.containsKey(path)) {
                    HtmlService htmlService = (HtmlService) AUTH_PATH_MAP.get(path).newInstance();
                    return htmlService.render(sessionUser);
                }
                return Files.readAllBytes(file.toPath());
            }
        }
        throw new NoSuchFileException(path);
    }

    public static byte[] getStaticResource(String path) throws IOException {
        for (String rootPath : PATH_LIST) {
            File file = new File(rootPath + path);
            if (file.exists() && file.isFile()) {
                return Files.readAllBytes(file.toPath());
            }
        }
        throw new NoSuchFileException(path);
    }
}
