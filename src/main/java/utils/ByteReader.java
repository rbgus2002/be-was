package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ByteReader {
    private ByteReader() {
    }

    public static String readInputStream(InputStream inputStream) throws IOException {
        StringBuilder messageBuilder = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        while (br.ready()) {
            int ch = br.read();
            messageBuilder.append((char) ch);
        }
        return messageBuilder.toString();
    }
}
