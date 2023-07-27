package service;

import model.enums.Mime;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static constant.SourcePath.RESOURCE_STATIC_RELATIVE_PATH;
import static constant.SourcePath.RESOURCE_TEMPLATE_RELATIVE_PATH;

public class FileService {
    public byte[] openFile(String path, Mime extension) throws IOException {
        if (extension.isHTML()) {
            return openTemplate(path);
        }
        return openStatic(path);
    }

    private byte[] openStatic(String path) throws IOException {
        String resourcePath = RESOURCE_STATIC_RELATIVE_PATH + path;
        return getFileIn(resourcePath);
    }

    private byte[] openTemplate(String path) throws IOException {
        String resourcePath = RESOURCE_TEMPLATE_RELATIVE_PATH + path;
        return getFileIn(resourcePath);
    }

    private byte[] getFileIn(String relativePath) throws IOException {
        Path path = Paths.get(relativePath);
        return Files.readAllBytes(path);
    }
}
