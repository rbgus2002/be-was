package container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HTTPServletRequest;

import java.util.concurrent.ConcurrentHashMap;

public class DispatcherServlet {
    private static final ConcurrentHashMap<String, Servlet> map = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private static final String LOGIN_PATH = "/user/create";

    public DispatcherServlet() {
        map.put("/user/login", new LogInTestServlet());
        map.put(LOGIN_PATH, new LogInServlet());
    }

    public Servlet findServlet(HTTPServletRequest request) {
        if (map.contains(request.getUrl())) {
            return map.get(request.getUrl());
        }

        map.put(request.getUrl(), new BaseServlet());
        return map.get(request.getUrl());
    }
}
