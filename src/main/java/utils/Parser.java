package utils;

public class Parser {

    private Parser() {}

    public static String getPathFromRequestLine(String requestLine) {
        String[] tokens = requestLine.split(" ");
        return tokens[1];
    }
}
