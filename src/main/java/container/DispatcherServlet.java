package container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HTTPServletRequest;

import java.util.concurrent.ConcurrentHashMap;

public class DispatcherServlet {
    private static ConcurrentHashMap<String, Servlet> map = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String LOGIN_PATH = "/user/create";

    public static Servlet findServlet(HTTPServletRequest request) {
        if (request.getUrl().equals(LOGIN_PATH) && !isMapHasUrl(LOGIN_PATH)) {
            map.put(LOGIN_PATH, new LogInServlet());
            return map.get(LOGIN_PATH);
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
