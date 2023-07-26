package service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileService {

    public static byte[] readStaticFile(String route) {
        try {
            return Files.readAllBytes(new File(route).toPath());
        }
        catch (IOException e) {
            return null;
        }
    }
}
