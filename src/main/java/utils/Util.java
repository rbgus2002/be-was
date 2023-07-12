package utils;

public class Util {

    private Util() {}

    public static String getPathFromRequestLine(String requestLine) {
        String[] tokens = requestLine.split(" ");
        return tokens[1];
    }
}
