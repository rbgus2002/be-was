package webserver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Session {

    private final Map<String, String> sessionStorage = new ConcurrentHashMap<>();


}
