package view;

import java.util.Map;

public interface Servlet {
    byte[] doGet(Map<String, Object> data);
}
