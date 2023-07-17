package container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HTTPServletRequest;

import java.util.concurrent.ConcurrentHashMap;

public class DispatcherServlet {
    private static ConcurrentHashMap<String, Servlet> map = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);


    public static synchronized Servlet findServlet(HTTPServletRequest request) {
        if (request.getUrl().equals("/user/create") && !isMapHasUrl("/user/create")) {
            map.put("/user/create", new LogInServlet());
            return map.get("/user/create");
        }
        if (isMapHasUrl(request.getUrl())) {
            return map.get(request.getUrl());
        }

        map.put(request.getUrl(), new PrimaryServlet());
        return map.get(request.getUrl());
    }

    private static boolean isMapHasUrl(String url) {
        if (map.contains(url)) {
            return true;
        }
        return false;
    }


}
