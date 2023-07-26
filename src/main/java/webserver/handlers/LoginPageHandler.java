package webserver.handlers;

import webserver.http.message.HttpRequest;
import webserver.http.message.HttpResponse;
import webserver.http.message.Mime;
import webserver.model.Model;
import webserver.session.Session;
import webserver.template.TemplateRenderer;
import webserver.utils.ExtensionSeparator;
import webserver.utils.FileUtils;

public class LoginPageHandler implements Handler{
    TemplateRenderer templateRenderer = TemplateRenderer.getInstance();

    @Override
    public HttpResponse handle(HttpRequest request, Session session) {
        String path = request.getURL().getPath();
        byte[] file = FileUtils.readFileFromTemplate(path);

        Model model = new Model();
        model.setAttribute("loginStatus", "false");
        if (session.isValid()) {
            return HttpResponse.redirect("/index.html");
        }

        String html = templateRenderer.render(new String(file), model);

        String ext = ExtensionSeparator.separateExtension(path);
        Mime mime = Mime.findByExt(ext);
        return HttpResponse.okWithFile(html.getBytes(), mime);
    }
}
