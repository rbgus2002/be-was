package service;

import model.HttpRequest;
import model.HttpResponse;
import model.enums.HttpStatusCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static constant.SourcePath.getRelativePath;

public class FileService {
    public String openFile(String path) throws FileNotFoundException {
        String resourcePath = getRelativePath(path);
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
