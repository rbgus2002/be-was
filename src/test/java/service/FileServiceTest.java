package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static constant.SourcePath.BASIC_INDEX_PATH;
import static org.junit.jupiter.api.Assertions.*;

class FileServiceTest {
    private String startTag = "<!DOCTYPE html>";

    private FileService fileService;

    @BeforeEach
    void init() {
        fileService = new FileService();
    }

    @Test
    @DisplayName("위치에 있는 파일을 찾는다.")
    void findFile() {
        String basicIndexPath = BASIC_INDEX_PATH;

        assertDoesNotThrow(() -> {
            String fileContent = fileService.openFile(basicIndexPath);
            assertTrue(fileContent.startsWith(startTag));
        });
    }

    @Test
    @DisplayName("위치에 있는 파일을 찾는다.")
    void dontFindFile() {
        String basicIndexPath = "/somewhere";

        assertThrows(FileNotFoundException.class, () -> {
            String fileContent = fileService.openFile(basicIndexPath);
            assertTrue(fileContent.startsWith(startTag));
        });
    }
}