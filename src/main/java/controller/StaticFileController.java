package controller;

import webserver.model.Request;
import webserver.model.Response;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static http.HttpParser.parseMime;
import static http.HttpUtil.*;
import static service.FileService.readStaticFile;

public class StaticFileController {
    public static Response genereateResponse(Request request) throws IOException {
        String targetUri = request.getTargetUri();

        MIME mime = parseMime(targetUri);
        if(mime == null) {
            return null;
        }

        String targetPath;
        byte[] body = null;

        targetPath = STATIC_FILEPATH + targetUri;
        if(new File(targetPath).exists()) {
            body = readStaticFile(targetPath);
        }
        targetPath = TEMPLATE_FILEPATH + targetUri;
        if(new File(targetPath).exists()) {
            body = readStaticFile(targetPath);
        }
        if(body == null) {
            return null;
        }

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put(HEADER_CONTENT_TYPE, mime.getMime() + HEADER_CHARSET);
        headerMap.put(HEADER_CONTENT_LENGTH, String.valueOf(body.length));

        return new Response(STATUS.OK, headerMap, body);
    }
}
