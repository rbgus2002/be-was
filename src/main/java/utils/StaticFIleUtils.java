package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;
import webserver.reponse.HttpResponse;
import webserver.reponse.Type;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class StaticFIleUtils {
    private static final Logger logger = LoggerFactory.getLogger(StaticFIleUtils.class);
    public static boolean isExistedStaticFileRequest(String url) throws IOException {
        String[] urlParts = url.split("[.]");
        return urlParts.length > 1 &&
                (Files.exists(new File("src/main/resources/templates" + url).toPath()) || Files.exists(new File("src/main/resources/static" + url).toPath()));
    }

    public static void getStaticByte(String url, HttpResponse response) throws IOException {
        String[] urlParts = url.split("[.]");
        if(Files.exists(new File("src/main/resources/templates" + url).toPath())){
            response.setBodyByFile(Files.readAllBytes(new File("src/main/resources/templates" + url).toPath()), Type.getType(urlParts[urlParts.length-1]));
            return;
        }
        response.setBodyByFile(Files.readAllBytes(new File("src/main/resources/static" + url).toPath()),Type.getType(urlParts[urlParts.length-1]));
    }
}
