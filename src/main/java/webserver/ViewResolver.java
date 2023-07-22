package webserver;

import view.*;

import static webserver.ServerConfig.TEMPLATE_PATH;

public class ViewResolver {
    private static final String REDIRECT_URL_PREFIX = "redirect:";

    public static View resolveViewName(String viewName) {
        if (viewName.startsWith(REDIRECT_URL_PREFIX)) {
            viewName = viewName.substring(REDIRECT_URL_PREFIX.length()).trim();
            return new RedirectView(viewName);
        }

        if (viewName.endsWith(".html")) {
            return new HtmlView(TEMPLATE_PATH + viewName);
        }

        return new PlainTextView(viewName);
    }

}
