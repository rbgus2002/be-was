package utils;

public class StringUtils {
    public static final String NEW_LINE = System.getProperty("line.separator");

    //TODO: 필요 없다! str.isEmpty() 사용하면 된다.
    public static boolean isEmpty(String str) {
        return str.equals("") || str.equals(" ");
    }
}
