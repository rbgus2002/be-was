package container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HTTPServletRequest;

import java.util.concurrent.ConcurrentHashMap;

public class DispatcherServlet {
    private static ConcurrentHashMap<String, Servlet> map = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);


    public static Servlet findServlet(HTTPServletRequest request) {
        if (request.getUrl().equals("/user/create") && !map.contains("/user/create")) {
            map.put("/user/create", new LogInServlet());
            return map.get("/user/create");
        }
        if (map.contains(request.getUrl())) {
            return map.get(request.getUrl());
        }

        map.put(request.getUrl(), new PrimaryServlet());
        return map.get(request.getUrl());
    }



}
