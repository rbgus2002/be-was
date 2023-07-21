package webserver.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ByteReader {
    private static final String CRLF = "\r\n";

    private ByteReader() {
    }

    public static String readInputStream(InputStream inputStream) throws IOException {
        StringBuilder messageBuilder = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String line;
        while (!(line = br.readLine()).equals("")) {
            messageBuilder.append(line).append(CRLF);
        }
        messageBuilder.append(CRLF);
        while (br.ready()) {
            messageBuilder.append((char) br.read());
        }
        return messageBuilder.toString();
    }
}
