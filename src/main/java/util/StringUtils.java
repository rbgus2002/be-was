package util;

import java.util.List;
import java.util.Map;

public class StringUtils {
    public static final String NEXTLINE = System.getProperty("line.separator");
    public static final String NO_CONTENT = "";
    public static final String SPACE = " ";
    public static final String QUESTION_MARK = "\\?"; // 정규표현식 예약어
    public static final String AMPERSAND_MARK = "&";
    public static final String EQUAL_MARK = "=";
    public static final String COLON_MARK = ":";
    public static final String COMMA_MARK = "\\.";

    private StringUtils() {}

    public static String[] splitBy(String text, String regex){
        return text.trim().split(regex);
    }

    public static String mapToHeaderFormat(Map<String, String> contents){
        StringBuilder stringBuilder = new StringBuilder();
        for(var content : contents.entrySet()){
            stringBuilder.append(content.getKey())
                    .append(COLON_MARK)
                    .append(SPACE)
                    .append(content.getValue())
                    .append(NEXTLINE);
        }
        return stringBuilder.toString();
    }
}
