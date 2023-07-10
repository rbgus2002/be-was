package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;
import static utils.StringUtils.appendNewLine;

class RequestHandlerTest {

    @Test
    @DisplayName("페이지를 요청했을 경우 해당 페이지를 반환해야 한다.")
    void test() {
        //given
        String request = appendNewLine("GET /index.html HTTP/1.1", "Host: localhost", "", "");
        InputStream inputStream = new ByteArrayInputStream(request.getBytes());
        OutputStream outputStream = new ByteArrayOutputStream();

        Socket socket = new Socket() {
            @Override
            public InputStream getInputStream() {
                return inputStream;
            }

            @Override
            public OutputStream getOutputStream() {
                return outputStream;
            }
        };
        RequestHandler requestHandler = new RequestHandler(socket);

        //when
        requestHandler.run();
        String response = outputStream.toString();

        //then
        assertNotNull(response);
        assertNotEquals(0, response.length());
    }

    @Test
    @DisplayName("MIME에 따라 다양한 파일 타입에 대해 contentType을 다르게 적용해주어야 한다.")
    void contextType() {
        //given
        String request = appendNewLine("GET /css/bootstrap.min.css HTTP/1.1", "Host: localhost", "", "");
        InputStream inputStream = new ByteArrayInputStream(request.getBytes());
        OutputStream outputStream = new ByteArrayOutputStream();

        Socket socket = new Socket() {
            @Override
            public InputStream getInputStream() {
                return inputStream;
            }

            @Override
            public OutputStream getOutputStream() {
                return outputStream;
            }
        };
        RequestHandler requestHandler = new RequestHandler(socket);

        //when
        requestHandler.run();
        String response = outputStream.toString();

        //then
        assertNotNull(response);
        assertNotEquals(0, response.length());
    }

    @Test
    @DisplayName("MIME에 따라 다양한 파일 타입에 대해 contentType을 다르게 적용해주어야 한다.")
    void contextTypeJavaScript() {
        //given
        String request = appendNewLine("GET /js/jquery-2.2.0.min.js HTTP/1.1", "Host: localhost", "", "");
        InputStream inputStream = new ByteArrayInputStream(request.getBytes());
        OutputStream outputStream = new ByteArrayOutputStream();

        Socket socket = new Socket() {
            @Override
            public InputStream getInputStream() {
                return inputStream;
            }

            @Override
            public OutputStream getOutputStream() {
                return outputStream;
            }
        };
        RequestHandler requestHandler = new RequestHandler(socket);

        //when
        requestHandler.run();
        String response = outputStream.toString();

        //then
        assertNotNull(response);
        assertNotEquals(0, response.length());
    }

}
