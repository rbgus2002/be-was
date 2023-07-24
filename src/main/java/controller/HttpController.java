package controller;

import java.util.Map;

public interface HttpController {
    String process(Map<String, String> requestParams);
}
