package service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileService {
    private static final String STATIC_FILEPATH = "./src/main/resources/static";
    private static final String TEMPLATE_FILEPATH = "./src/main/resources/templates";

    public static byte[] loadStaticFile(String route) throws IOException {
        // 요청 경로의 파일을 반환
        File f;
        byte[] body;
        // 두 가지의 경로 모두를 조회해야 합니다.
        if (!(f = new File(STATIC_FILEPATH + route)).exists()) {
            f = new File(TEMPLATE_FILEPATH + route);
        }
        body = Files.readAllBytes(f.toPath());
        return body;
    }
}
