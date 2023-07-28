package view;

import model.HttpRequest;

import java.util.HashMap;
import java.util.Map;

import static constant.Uri.INDEX_HTML_URI;
import static constant.Uri.USER_LIST_URI;

public class DynamicHtml {
    private static Map<String, Servlet> servlets = new HashMap<>();

    private DynamicHtml() {
    }

    static {
        servlets.put(INDEX_HTML_URI, new IndexServlet());
        servlets.put(USER_LIST_URI, new UserServlet());
    }

    public static byte[] select(HttpRequest httpRequest, Map<String, Object> viewParameters) {
        Servlet servlet = servlets.get(httpRequest.getUri());
        return servlet.doGet(viewParameters);
    }
}
