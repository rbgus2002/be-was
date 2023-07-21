package webserver.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class FileNameScannerTest {

    @DisplayName("resources/static에 들어있는 모든 정적 파일 이름 조회 기능 테스트")
    @Test
    void scanAllFilesIn_Resource_static_directory() {
        List<String> fileNames = FileNameScanner.scan("src/main/resources/static");

        Assertions.assertThat(fileNames)
                .contains(
                        "/favicon.ico",
                        "/css/styles.css",
                        "/css/bootstrap.min.css",
                        "/js/jquery-2.2.0.min.js",
                        "/images/80-text.png",
                        "/js/scripts.js",
                        "/js/bootstrap.min.js",
                        "/fonts/glyphicons-halflings-regular.eot",
                        "/fonts/glyphicons-halflings-regular.ttf",
                        "/fonts/glyphicons-halflings-regular.svg",
                        "/fonts/glyphicons-halflings-regular.woff",
                        "/fonts/glyphicons-halflings-regular.woff2"
                );

    }
}
