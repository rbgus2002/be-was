package webserver.http.request;

import webserver.http.HttpMethod;
import webserver.http.request.exception.IllegalRequestParameterException;

import java.io.*;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class HttpRequestParserImpl implements HttpRequestParser {
    private static final String CONTENT_LENGTH = "Content-Length";

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public HttpRequest parse(InputStream inputStream) throws IOException, IllegalRequestParameterException {
        HttpRequest.Builder builder = HttpRequest.builder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        parseRequestLine(builder, bufferedReader);
        int bodyLength = parseHeaders(builder, bufferedReader);

        char[] chars = new char[bodyLength];
        bufferedReader.read(chars);
        builder.body(charArrayToByteArray(chars));
        return builder.build();
    }

    private static void parseRequestLine(HttpRequest.Builder builder, BufferedReader bufferedReader) throws IOException, IllegalRequestParameterException {
        String[] startLine = bufferedReader.readLine().split(" ");
        String[] requestUri = startLine[1].trim().split("\\?");

        builder.method(HttpMethod.valueOf(startLine[0].trim()))
                .uri(requestUri[0].trim())
                .version(startLine[2].substring(startLine[2].indexOf('/') + 1).trim());

        if(requestUri.length == 2) {
            parseParameters(builder, requestUri);
        }
    }

    private static void parseParameters(HttpRequest.Builder builder, String[] requestUri) throws IllegalRequestParameterException {
        String decodeParameterString = URLDecoder.decode(requestUri[1], StandardCharsets.UTF_8);
        String[] decodeParameters = decodeParameterString.split("&");
        for (String decodeParameter : decodeParameters) {
            parseRequestParameter(builder, decodeParameter);
        }
    }

    private static void parseRequestParameter(HttpRequest.Builder builder, String decodeParameter) throws IllegalRequestParameterException {
        String[] keyValue = decodeParameter.split("=");
        if(keyValue.length != 2) {
            throw new IllegalRequestParameterException();
        }
        builder.addParameter(keyValue[0].trim(), keyValue[1].trim());
    }

    private static int parseHeaders(HttpRequest.Builder builder, BufferedReader bufferedReader) throws IOException {
        int contentLength = 0;
        String line;
        while(!(line = bufferedReader.readLine()).isBlank()) {
            String[] header = line.split(": ");
            builder.addHeader(header[0].trim(), header[1].trim());
            if(header[0].trim().equals(CONTENT_LENGTH)) {
                contentLength = Integer.parseInt(header[1].trim());
            }
        }
        return contentLength;
    }

    private static byte[] charArrayToByteArray(char[] chars) {
        if (chars == null) return null;
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(), byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(byteBuffer.array(), (byte) 0);
        return bytes;
    }
}
