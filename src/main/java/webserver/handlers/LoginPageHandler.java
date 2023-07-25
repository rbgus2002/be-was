package webserver.handlers;

import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.http.message.Mime;
import webserver.session.Session;
import webserver.model.Model;
import webserver.utils.ExtensionSeparator;
import webserver.utils.FileUtils;

public class LoginPageHandler implements Handler{
    @Override
    public HttpResponse handle(HttpRequest request, Session session, Model model) {
        String path = request.getURL().getPath();
        byte[] file = FileUtils.readFileFromTemplate(path);
        String ext = ExtensionSeparator.separateExtension(path);
        Mime mime = Mime.findByExt(ext);
        return HttpResponse.okWithFile(file, mime);
    }
}
