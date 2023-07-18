package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class StaticFIleUtils {
    private static final Logger logger = LoggerFactory.getLogger(StaticFIleUtils.class);
    public static boolean isExistedStaticFileRequest(String url) throws IOException {
        String[] splitedUrl = url.split("[.]");
        return splitedUrl.length > 1 &&
                (Files.exists(new File("src/main/resources/templates" + url).toPath()) || Files.exists(new File("src/main/resources/static" + url).toPath()));
    }

    public static byte[] getStaticByte(String url) throws IOException {
        if(Files.exists(new File("src/main/resources/templates" + url).toPath())){
            return Files.readAllBytes(new File("src/main/resources/templates" + url).toPath());
        }
        return Files.readAllBytes(new File("src/main/resources/static" + url).toPath());
    }
}
