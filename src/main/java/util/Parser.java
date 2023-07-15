package util;

import model.HttpHeader;
import model.HttpRequest;
import model.RequestUri;
import model.enums.Method;

import java.io.BufferedReader;
import java.io.IOException;

import static util.StringUtils.NEXTLINE;
import static util.StringUtils.SPACE;

public class Parser {
    private static final int METHOD_INDEX = 0;
    private static final int URI_INDEX = 1;
    private static final int PROTOCOL_INDEX = 2;

    public static HttpRequest getHttpRequest(BufferedReader bufferedReader) throws IOException {
        String line = null;
        String requestLine = bufferedReader.readLine();
        String[] tokens = requestLine.split(SPACE);
        RequestUri requestUri = RequestUri.of(tokens[URI_INDEX]);

        StringBuilder stringBuilder = new StringBuilder();
        while (!(line = bufferedReader.readLine()).equals("")) {
            stringBuilder.append(line);
            stringBuilder.append(NEXTLINE);
        }

        String[] splitMessage = stringBuilder.toString().split(NEXTLINE);
        HttpHeader header = HttpHeader.of(splitMessage);

        return new HttpRequest(requestUri, tokens[PROTOCOL_INDEX], Method.valueOf(tokens[METHOD_INDEX]), header, "");
    }

}
