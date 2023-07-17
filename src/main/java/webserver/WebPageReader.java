package webserver;

import exception.ExceptionName;

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

    public static byte[] readByPath(String url) throws IOException {
        for (String path : WEB_PATH) {
            File file = new File(path + url);
            if (file.exists() && file.isFile()) {
                return Files.readAllBytes(file.toPath());
            }
        }
        throw new IOException(ExceptionName.FILE_NOT_FOUND);
    }

}
