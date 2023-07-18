package service;

import model.enums.MIME;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class FileServiceTest {
    public static final String INDEX_HTML = "/index.html";
    private String startTag = "<!DOCTYPE html>";

    private FileService fileService;

    @BeforeEach
    void init() {
        fileService = new FileService();
    }

    @Test
    @DisplayName("위치에 있는 파일을 찾는다.")
    void findFile() {

        assertDoesNotThrow(() -> {
            String fileContent = fileService.openFile(INDEX_HTML, MIME.HTML);
            assertTrue(fileContent.startsWith(startTag));
        });
    }

    @Test
    @DisplayName("HTML mime 타입이여야 template 경로로 파일을 찾는다.")
    void dontFindFile() {
        String basicIndexPath = "/somewhere.html";

        assertThrows(FileNotFoundException.class, () -> {
            String fileContent = fileService.openFile(basicIndexPath, MIME.CSS);
            assertTrue(fileContent.startsWith(startTag));
        });
    }
}