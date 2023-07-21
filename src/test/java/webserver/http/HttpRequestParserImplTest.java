package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestParser;
import webserver.http.request.HttpRequestParserImpl;
import webserver.http.request.exception.IllegalRequestParameterException;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.assertj.core.api.Assertions.*;


@DisplayName("HttpRequestParserImpl 테스트")
class HttpRequestParserImplTest {
    final HttpRequestParser httpRequestParser = new HttpRequestParserImpl();
    static final String httpMessage =
            "GET /index.html?parameter1=hello1&parameter2=hello2 HTTP/1.1\r\n" +
            "Host: localhost:8080\r\n" +
            "Connection: keep-alive\r\n" +
            "Content-Length: 12\r\n" +
            "sec-ch-ua: \"Not.A/Brand\";v=\"8\", \"Chromium\";v=\"114\", \"Google Chrome\";v=\"114\"\r\n" +
            "sec-ch-ua-mobile: ?0\r\n" +
            "sec-ch-ua-platform: \"macOS\"\r\n" +
            "Upgrade-Insecure-Requests: 1\r\n" +
            "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36\r\n" +
            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7\r\n" +
            "Sec-Fetch-Site: none\r\n" +
            "Sec-Fetch-Mode: navigate\r\n" +
            "Sec-Fetch-User: ?1\r\n" +
            "Sec-Fetch-Dest: document\r\n" +
            "Accept-Encoding: gzip, deflate, br\r\n" +
            "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7\r\n" +
            "Cookie: Idea-13020a74=01685f19-8588-4907-88e8-60ee76288f21\r\n"
            + "\r\n" +
            "BodyBodyBody";

    @Nested
    @DisplayName("parse method")
    class parse {
        @Test
        @DisplayName("HTTP 요청 메세지의 정보를 담고있는 HttpRequest 객체를 반환한다")
        void returnHttpRequest() throws IOException, IllegalRequestParameterException {
            //given
            HashMap<String, String> expectHeaders = getExpectHeaders();
            InputStream inputStream = new ByteArrayInputStream(httpMessage.getBytes());

            //when
            HttpRequest httpRequest = httpRequestParser.parse(inputStream);

            //then
            verifyRequestLine(httpRequest, HttpMethod.GET, "/index.html", "1.1");
            assertThat(httpRequest.getHeaderNames()
                    .containsAll(List.of("Host", "Accept-Encoding", "Accept-Language")))
                    .isTrue();
            verifyHeaders(httpRequest, expectHeaders);

            byte[] body = httpRequest.getBody();
            String resultBody = new String(body);
            assertThat(resultBody).isEqualTo("BodyBodyBody");
        }
    }

    @Nested
    @DisplayName("parseRequestParameter method")
    class ParseRequestParameter {
        @Nested
        @DisplayName("파라미터 문자열이 잘못되었을 경우")
        class IsParameterStringWrong {
            @Test
            @DisplayName("IllegalRequestParameterException 예외가 발생한다")
            void throwIllegalRequestParameterException() throws ReflectiveOperationException {
                //given
                HttpRequest.Builder builder = HttpRequest.builder();
                String wrongParameters = "wrong";
                Method parseRequestParameterMethod = HttpRequestParserImpl.class
                        .getDeclaredMethod("parseRequestParameter", HttpRequest.Builder.class, String.class);
                parseRequestParameterMethod.setAccessible(true);

                //when
                //then
                try {
                    parseRequestParameterMethod.invoke(httpRequestParser, builder, wrongParameters);
                    throw new RuntimeException("에러가 발생해야됨");
                } catch (InvocationTargetException e) {
                    assertThat(e.getTargetException())
                            .isInstanceOf(IllegalRequestParameterException.class);
                }
            }
        }

        @Nested
        @DisplayName("파라미터 문자열이 올바른 경우")
        class IsParameterStringCorrect {
            @Test
            @DisplayName("아무 일도 발생하지 않는다")
            void NottingOccurred() throws ReflectiveOperationException {
                //given
                HttpRequest.Builder builder = HttpRequest.builder();
                String wrongParameters = "correct=correct";
                Method parseRequestParameterMethod = HttpRequestParserImpl.class
                        .getDeclaredMethod("parseRequestParameter", HttpRequest.Builder.class, String.class);
                parseRequestParameterMethod.setAccessible(true);

                //when
                //then
                parseRequestParameterMethod.invoke(httpRequestParser, builder, wrongParameters);
            }
        }
    }

    @SuppressWarnings("SameParameterValue")
    private static void verifyRequestLine(HttpRequest httpRequest,
                                          HttpMethod method,
                                          String uri,
                                          String version) {
        assertThat(httpRequest.getMethod()).isEqualTo(method);
        assertThat(httpRequest.getUri()).isEqualTo(uri);
        assertThat(httpRequest.getVersion()).isEqualTo(version);

        verifyParameter(httpRequest, "parameter1", "hello1");
        verifyParameter(httpRequest, "parameter2", "hello2");

        Optional<String> notExist = httpRequest.getParameter("notExist");
        assertThat(notExist.isEmpty()).isTrue();
    }

    private static void verifyParameter(HttpRequest httpRequest, String parameterName, String hello2) {
        String parameter = httpRequest.getParameter(parameterName)
                .orElseThrow(RuntimeException::new);
        assertThat(parameter).isNotNull();
        assertThat(parameter).isEqualTo(hello2);
    }

    private static void verifyHeaders(HttpRequest httpRequest,
                                      Map<String, String> headerInfos) {
        for (String headerName : headerInfos.keySet()) {
            verifyHeader(httpRequest, headerInfos, headerName);
        }
    }

    private static void verifyHeader(HttpRequest httpRequest, Map<String, String> headerInfos, String headerName) {
        String expectValue = headerInfos.get(headerName);
        String resultValue = httpRequest.getHeader(headerName);
        assertThat(resultValue).isEqualTo(expectValue);
    }

    private static HashMap<String, String> getExpectHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Host", "localhost:8080");
        headers.put("Connection", "keep-alive");
        headers.put("sec-ch-ua", "\"Not.A/Brand\";v=\"8\", \"Chromium\";v=\"114\", \"Google Chrome\";v=\"114\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"macOS\"");
        headers.put("Upgrade-Insecure-Requests", "1");
        headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
        headers.put("Sec-Fetch-Site", "none");
        headers.put("Sec-Fetch-Mode", "navigate");
        headers.put("Sec-Fetch-User", "?1");
        headers.put("Sec-Fetch-Dest", "document");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
        headers.put("Cookie", "Idea-13020a74=01685f19-8588-4907-88e8-60ee76288f21");
        return headers;
    }
}
