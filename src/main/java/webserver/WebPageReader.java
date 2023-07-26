package webserver;

import support.exception.NotFoundException;
import support.exception.ServerErrorException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class WebPageReader {

    private static final List<String> WEB_PATH = new ArrayList<>();

    static {
        WEB_PATH.add("src/main/resources/templates");
        WEB_PATH.add("src/main/resources/static");
    }

    private WebPageReader() {

    }

    public static byte[] readByPath(String url) throws NotFoundException, ServerErrorException {
        for (String path : WEB_PATH) {
            File file = new File(path + url);
            if (file.exists() && file.isFile()) {
                try {
                    return Files.readAllBytes(file.toPath());
                } catch (IOException e) {
                    throw new ServerErrorException();
                }
            }
        }
        throw new NotFoundException();
    }

    public static List<String> readStringLineByPath(String url) throws NotFoundException, ServerErrorException {
        for (String path : WEB_PATH) {
            File file = new File(path + url);
            if (file.exists() && file.isFile()) {
                try {
                    return Files.readAllLines(file.toPath());
                } catch (IOException e) {
                    throw new ServerErrorException();
                }
            }
        }
        throw new NotFoundException();
    }

}
