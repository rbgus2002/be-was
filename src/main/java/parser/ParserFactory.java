package parser;

public class ParserFactory {
    public Parser createParser(String method) {
        if (method.equals("POST")) {
            return new PostParser();
        }

        return new GetParser();
    }
}
