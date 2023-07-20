package controller;

import http.HttpUtil;
import service.FileService;
import webserver.model.Request;
import webserver.model.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static http.HttpUtil.*;

public class FileController {
    public static Response genereateResponse(Request request) throws IOException {
        String targetUri = request.getTargetUri();

        // Static Files
        String[] tokens = targetUri.split("\\.");
        String extension = tokens[tokens.length-1];
        HttpUtil.MIME mime = HttpUtil.MIME.getMimeByExtension(extension);
        if (mime != null) {
            byte[] body = FileService.loadFile(request);

            Map<String, String> headerMap = new HashMap<>();
            headerMap.put(HEADER_CONTENT_TYPE, mime.getMime() + HEADER_CHARSET);
            headerMap.put(HEADER_CONTENT_LENGTH, String.valueOf(body.length));

            return new Response(STATUS.OK, headerMap, body);
        }

        return null;
    }
}
