package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class FileReaderTest {
    @Test
    @DisplayName("path에 해당하는 위치에 있는 파일을 가져와야 한다.")
    void getFileByPath() throws IOException {
        // given
        String path = "/index.html";
        byte[] expected = Files.readAllBytes(new File("src/main/resources/templates" + path).toPath());

        // when
        byte[] file = FileReader.getFileByPath(path);

        // then
        assertArrayEquals(expected, file);
    }
}
