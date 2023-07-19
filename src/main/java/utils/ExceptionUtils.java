package utils;

import java.util.HashMap;
import java.util.Map;

public class ExceptionUtils {
    private static Map<Integer, String> exceptionMap = new HashMap();
    static {
        exceptionMap.put(400, "잘못된 입력입니다.");
        exceptionMap.put(404, "요청한 파일을 찾을 수 없습니다.");
    }

    public static String getErrorMessage(int statusCode) {
        return exceptionMap.get(statusCode);
    }
}
