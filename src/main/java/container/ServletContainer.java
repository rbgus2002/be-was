package container;

import webserver.HTTPServletRequest;

import java.util.concurrent.ConcurrentHashMap;

public class ServletContainer {
    private static ConcurrentHashMap<String, Servlet> map = new ConcurrentHashMap<>();

    public static Servlet findServlet(HTTPServletRequest request) {
        if (map.contains(request.getUri())) {
            return map.get(request.getUri());
        }

        map.put(request.getUri(), new PrimaryServlet());
        return map.get(request.getUri());
    }

}
