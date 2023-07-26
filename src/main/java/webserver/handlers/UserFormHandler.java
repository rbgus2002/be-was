package webserver.handlers;

import webserver.http.message.Mime;
import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.session.Session;
import webserver.model.Model;
import webserver.template.TemplateRenderer;
import webserver.utils.ExtensionSeparator;
import webserver.utils.FileUtils;

public class UserFormHandler implements Handler{
    TemplateRenderer templateRenderer = TemplateRenderer.getInstance();

    @Override
    public HttpResponse handle(HttpRequest request, Session session, Model model) {
        String path = request.getURL().getPath();
        String ext = ExtensionSeparator.separateExtension(path);
        Mime mime = Mime.findByExt(ext);

        model.setAttribute("loginStatus", "false");
        if (session.isValid()) {
            model.setAttribute("loginStatus", "true");
        }

        byte[] file = FileUtils.readFileFromTemplate(path);
        String html = templateRenderer.render(new String(file), model);

        return HttpResponse.okWithFile(html.getBytes(), mime);
    }
}
