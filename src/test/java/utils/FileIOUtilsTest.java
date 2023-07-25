package utils;

import http.HttpResponse;
import http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static utils.FileIOUtils.*;

class FileIOUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    @Test
    @DisplayName("Header의 첫 번째 라인을 통해 static 디렉터리 안의 file을 load 할 수 있어야 한다")
    void loadStaticFromPathTest() throws IOException {
        HttpResponse.ResponseBuilder response = loadFromPath(HttpStatus.OK, "/css/styles.css");
        logger.debug("static file : {}", response);
        assertNotNull(response);
    }

    @Test
    @DisplayName("Header의 첫 번째 라인을 통해 templates 디렉터리 안의 file을 load 할 수 있어야 한다")
    void loadTemplatesFromPathTest() throws IOException {
        HttpResponse.ResponseBuilder response = loadFromPath(HttpStatus.OK, "/index.html");
        logger.debug("templates file : {}", response);
        assertNotNull(response);
    }

}