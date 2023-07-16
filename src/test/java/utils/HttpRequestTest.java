package utils;


import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class HttpRequestTest {

    BufferedReader br;
    static Class<HttpRequest> clazz = HttpRequest.class;
    HttpRequest httpRequest;

    @BeforeEach
    void setUp() throws InvocationTargetException, InstantiationException, IllegalAccessException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        httpRequest = createInstance();
    }

    @AfterEach
    void tearDown() throws IOException {
//        br.close();
    }

    @Test
    @DisplayName("[/] Request Line 파싱")
    void parseRequestLine1() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        String requestLine = "GET / HTTP/1.1\r\n";
        Method parseRequestLine = getParseRequestLine();

        parseRequestLine.invoke(null, requestLine, httpRequest);

        verifyRequestLine("/", new HashMap<>());
    }

    @Test
    @DisplayName("[/index.html] Request Line 파싱")
    void parseRequestLine2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        String requestLine = "GET /index.html HTTP/1.1\r\n";
        Method parseRequestLine = getParseRequestLine();

        parseRequestLine.invoke(null, requestLine, httpRequest);

        verifyRequestLine("/index.html", new HashMap<>());
    }

    @Test
    @DisplayName("[/dir1/index.html] Request Line 파싱")
    void parseRequestLine3() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        String requestLine = "GET /dir1/index.html HTTP/1.1\r\n";
        Method parseRequestLine = getParseRequestLine();

        parseRequestLine.invoke(null, requestLine, httpRequest);

        verifyRequestLine("/dir1/index.html", new HashMap<>());
    }

    @Test
    @DisplayName("[/dir1/index.html?p1=v1&p2=v2] Request Line 파싱")
    void parseRequestLine4() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        String requestLine = "GET /dir1/index.html?p1=v1&p2=v2 HTTP/1.1\r\n";
        Method parseRequestLine = getParseRequestLine();

        parseRequestLine.invoke(null, requestLine, httpRequest);

        HashMap<String, String> expectedParams = new HashMap<>();
        expectedParams.put("p1", "v1");
        expectedParams.put("p2", "v2");
        verifyRequestLine("/dir1/index.html", expectedParams);
    }

    @Test
    @DisplayName("[/create?p1=v1&p2=v2] Request Line 파싱")
    void parseRequestLine5() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        String requestLine = "GET /create?p1=v1&p2=v2 HTTP/1.1\r\n";
        Method parseRequestLine = getParseRequestLine();

        parseRequestLine.invoke(null, requestLine, httpRequest);

        HashMap<String, String> expectedParams = new HashMap<>();
        expectedParams.put("p1", "v1");
        expectedParams.put("p2", "v2");
        verifyRequestLine("/create", expectedParams);
    }

    @Test
    @DisplayName("[/create?p1=&p2=] Request Line 파싱")
    void parseRequestLine6() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        String requestLine = "GET /create?p1=&p2= HTTP/1.1\r\n";
        Method parseRequestLine = getParseRequestLine();

        parseRequestLine.invoke(null, requestLine, httpRequest);

        HashMap<String, String> expectedParams = new HashMap<>();
        expectedParams.put("p1", null);
        expectedParams.put("p2", null);
        verifyRequestLine("/create", expectedParams);
    }

    private void verifyRequestLine(String path, HashMap<String, String> params) throws NoSuchFieldException, IllegalAccessException {
        Field m = clazz.getDeclaredField("method");
        m.setAccessible(true);
        assertEquals("GET", m.get(httpRequest));

        Field u = clazz.getDeclaredField("path");
        u.setAccessible(true);
        assertEquals(path, u.get(httpRequest));

        Field v = clazz.getDeclaredField("version");
        v.setAccessible(true);
        assertEquals("HTTP/1.1", v.get(httpRequest));

        Field p = clazz.getDeclaredField("params");
        p.setAccessible(true);
        assertEquals(params, p.get(httpRequest));
    }

    private static HttpRequest createInstance() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        return (HttpRequest) constructor.newInstance();
    }

    private Method getParseRequestLine() throws NoSuchMethodException {
        Method method = clazz.getDeclaredMethod("parseRequestLine", String.class, clazz);
        method.setAccessible(true);
        return method;
    }
}