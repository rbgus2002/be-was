package service;

import model.enums.MIME;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static constant.SourcePath.RESOURCE_STATIC_RELATIVE_PATH;
import static constant.SourcePath.RESOURCE_TEMPLATE_RELATIVE_PATH;

public class FileService {
    public String openFile(String path, MIME extension) throws FileNotFoundException {
        if (extension.isHTML()) {
            return openTemplate(path);
        }
        return openStatic(path);
    }

    private String openStatic(String path) throws FileNotFoundException {
        String resourcePath = RESOURCE_STATIC_RELATIVE_PATH + path;
        return getFileIn(resourcePath);
    }

    private String openTemplate(String path) throws FileNotFoundException {
        String resourcePath = RESOURCE_TEMPLATE_RELATIVE_PATH + path;
        return getFileIn(resourcePath);
    }

    private String getFileIn(String relativePath) throws FileNotFoundException {
        String result = "";
        File targetFile = new File(relativePath);
        Scanner fileScanner = new Scanner(targetFile);
        result = fileScanner.useDelimiter("\\Z").next();
        fileScanner.close();
        return result;
    }
}
