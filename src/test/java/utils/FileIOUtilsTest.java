package utils;

import http.HttpResponse;
import http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static utils.FileIOUtils.*;

class FileIOUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    @Test
    @DisplayName("Header의 첫 번째 라인을 통해 static 디렉터리 안의 file을 load 할 수 있어야 한다")
    void loadStaticFromPathTest() throws IOException {
        HttpResponse.ResponseBuilder builder = new HttpResponse.ResponseBuilder();
        HttpResponse.ResponseBuilder response = loadStaticFromPath(builder, "/css/styles.css");
        logger.debug("static file : {}", response);
        assertNotNull(response);
    }

    @Test
    @DisplayName("Header의 첫 번째 라인을 통해 templates 디렉터리 안의 file을 load 할 수 있어야 한다")
    void loadTemplatesFromPathTest() throws IOException {
        HttpResponse.ResponseBuilder builder = new HttpResponse.ResponseBuilder();
        HttpResponse.ResponseBuilder response = loadTemplatesFromPath(builder, "/index.html");
        logger.debug("templates file : {}", response);
        assertNotNull(response);
    }

}