package utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class StaticFIleUtilsTest {

    @Test
    @DisplayName("request 요청에 url을 통해 해당 요청이 html 파일 반환에 대한 요청인지, 해당 파일이 존재하는지에 대해 확인할 수 있어야 한다.")
    void htmlFile() throws IOException {
        assertTrue(StaticFIleUtils.isExistedStaticFileRequest("/index.html"));
        assertTrue(StaticFIleUtils.isExistedStaticFileRequest("/user/form.html"));
        assertTrue(StaticFIleUtils.isExistedStaticFileRequest("/user/list.html"));
        assertTrue(StaticFIleUtils.isExistedStaticFileRequest("/user/login.html"));
        assertTrue(StaticFIleUtils.isExistedStaticFileRequest("/user/profile.html"));
        assertTrue(StaticFIleUtils.isExistedStaticFileRequest("/user/login_failed.html"));
    }
}