package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static utils.StringUtils.NEW_LINE;

public class ByteReader {
    private ByteReader() {
    }
    
    public static String readInputStream(InputStream inputStream) throws IOException {
        StringBuilder messageReader = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String line;
        while ((line = br.readLine()) != null) {
            messageReader.append(line).append(NEW_LINE);
        }
        return messageReader.toString();
    }
}
