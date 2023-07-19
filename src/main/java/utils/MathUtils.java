package utils;

public abstract class MathUtils {

    public static int parseIntOrDefault(String str, int defaultValue) {
        if (str == null) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

}
