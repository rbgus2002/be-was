package webserver.handlers;

import webserver.http.Mime;
import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.utils.FileExtensionSeparator;
import webserver.utils.FileUtils;

public class UserFormHandler implements Handler{
    @Override
    public HttpResponse handle(HttpRequest request) {
        String path = request.getURL().getPath();
        byte[] file = FileUtils.readFileFromTemplate(path);
        String ext = FileExtensionSeparator.separateExtension(path);
        Mime mime = Mime.findByExt(ext);
        return HttpResponse.okWithFile(file, mime);
    }
}
