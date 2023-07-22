package webserver.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.utils.ByteReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


class ByteReaderTest {
    @DisplayName("readInputStream 테스트")
    @Test
    void readTest() throws IOException {
        String message = "abc\r\ndef\r\nghi\r\n\r\n";
        InputStream inputStream = new ByteArrayInputStream(message.getBytes());

        String inputMessage = ByteReader.readInputStream(inputStream);

        Assertions.assertEquals(message, inputMessage);
    }
}