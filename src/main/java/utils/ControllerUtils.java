package utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ControllerUtils {
    private ControllerUtils() {
    }

    public static Map<String, String> getParameterMap(String body, int size) {
        Map<String, String> parameterMap = new HashMap<>();
        for (String parameter : body.split("&")) {
            String[] keyValue = parameter.split("=");
            if (keyValue.length != 2) {
                throw new IllegalArgumentException();
            }
            parameterMap.put(keyValue[0].trim(), keyValue[1].trim());
        }

        verifyParameters(parameterMap, size);
        return parameterMap;
    }

    private static void verifyParameters(Map<String, String> parameterMap, int size) {
        if (parameterMap.values().size() != size ||
            parameterMap.values().stream()
                    .anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException();
        }
    }
}
