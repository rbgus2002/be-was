package service;

import model.enums.Mime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.NoSuchFileException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileServiceTest {
    public static final String INDEX_HTML = "/index.html";

    private FileService fileService;

    @BeforeEach
    void init() {
        fileService = new FileService();
    }

    @Test
    @DisplayName("위치에 있는 파일을 찾는다.")
    void findFile() {

        assertDoesNotThrow(() -> {
            byte[] fileContent = fileService.openFile(INDEX_HTML, Mime.HTML);
        });
    }

    @Test
    @DisplayName("HTML mime 타입이여야 template 경로로 파일을 찾는다.")
    void dontFindFile() {
        String basicIndexPath = "/somewhere.html";

        assertThrows(NoSuchFileException.class, () -> {
            byte[] fileContent = fileService.openFile(basicIndexPath, Mime.CSS);
        });
    }
}