package controller;

import webserver.http.model.Request;
import webserver.http.model.Response;

import java.io.File;
import java.io.IOException;

import static webserver.http.HttpParser.parseMime;
import static webserver.http.HttpUtil.*;
import static service.FileService.readStaticFile;

public class StaticFileController extends FileController {
    public static Response genereateResponse(Request request) throws IOException {
        String targetUri = request.getTargetUri();

        MIME mime = parseMime(targetUri);
        if(mime == null) {
            return null;
        }

        String targetPath;
        targetPath = STATIC_FILEPATH + targetUri;
        if(new File(targetPath).exists()) {
            byte[] body = readStaticFile(targetPath);
            return generate200Response(mime, body);
        }
        targetPath = TEMPLATE_FILEPATH + targetUri;
        if(new File(targetPath).exists()) {
            String httpDocument = generateHttpDocument(request);
            return generate200Response(mime, httpDocument.getBytes());
        }
        return null;

    }
}
