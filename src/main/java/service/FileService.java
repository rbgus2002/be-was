package service;

import webserver.model.Request;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static http.HttpUtil.*;
import static service.SessionService.getUserIdBySid;

public class FileService {

    public static byte[] readStaticFile(String route) throws IOException {
        return Files.readAllBytes(new File(route).toPath());
    }
}
