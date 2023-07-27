package webserver.request;

import controller.SignUpController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.HttpRequestMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HttpRequestMapperTest {


    @Test
    @DisplayName("url을 입력했을 때 해당 url과 매핑 된 컨트롤러를 반환해야 한다.")
    void getController() throws IOException{
        verifyController("POST /user/create?id=id&password=password&email=email&name=name HTTP/1.1", SignUpController.class);
    }

    void verifyController(String statusLine, Class clazz) throws IOException {
        HttpRequest request = HttpRequestParser.getRequest(new ByteArrayInputStream(statusLine.getBytes()));
        assertInstanceOf(clazz, HttpRequestMapper.getInstance().getController(request.getMethod(),request.getPath()));
    }
}