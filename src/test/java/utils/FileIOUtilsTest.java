package utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileIOUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    private FileIOUtils fileIOUtils;

    @BeforeEach
    void setup() {
        fileIOUtils = new FileIOUtils();
    }

    @Test
    @DisplayName("Header의 첫 번째 라인을 통해 static 디렉터리 안의 file을 load 할 수 있어야 한다")
    void loadStaticFromPathTest() throws IOException {
        byte[] body = fileIOUtils.loadStaticFromPath("GET /css/styles.css HTTP/1.1");
        logger.debug("static file : {}", body);
        assertNotNull(body);
    }

    @Test
    @DisplayName("Header의 첫 번째 라인을 통해 templates 디렉터리 안의 file을 load 할 수 있어야 한다")
    void loadTemplatesFromPathTest() throws IOException {
        byte[] body = fileIOUtils.loadTemplatesFromPath("GET /index.html HTTP/1.1");
        logger.debug("templates file : {}", body);
        assertNotNull(body);
    }

}