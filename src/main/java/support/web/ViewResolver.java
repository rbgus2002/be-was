package support.web;

import webserver.response.HttpResponse;
import webserver.response.MIME;
import webserver.response.strategy.OK;

import java.io.IOException;

import static webserver.WebPageReader.readByPath;

public abstract class ViewResolver {

    public static void buildView(HttpResponse response, String path) throws IOException {
        byte[] body = readByPath(path);
        String extension = path.substring(path.lastIndexOf("."));
        response.setBody(body);
        response.buildHeader(new OK(MIME.getContentType(extension), body.length));
    }

}
