package webserver.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    private static final List<String> PATH_LIST = new ArrayList<>();

    private FileService(){}

    static {
        PATH_LIST.add("src/main/resources/templates");
        PATH_LIST.add("src/main/resources/static");
    }

    public static byte[] getStaticResource(String path) throws IOException {
        for (String rootPath: PATH_LIST) {
            File file = new File(rootPath + path);
            if (file.exists() && file.isFile()) {
                return Files.readAllBytes(file.toPath());
            }
        }
        throw new NoSuchFileException(path);
    }
}
