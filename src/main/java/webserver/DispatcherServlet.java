package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.HttpStatus;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;

import static webserver.http.HttpStatus.OK;

public class DispatcherServlet {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private final String PATH = "src/main/resources/templates";

    public void doDispatch(HttpRequest request, OutputStream out) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, IOException {
        HandlerMapping.getHandler(request);
        byte[] body = Files.readAllBytes(new File(PATH + request.getPath()).toPath());

        HttpResponse httpResponse = new HttpResponse(OK, body);
        httpResponse.response(out); // TODO : 요청과 응답에 대한 처리의 책임을 어느 객체에 줄지 좀 더 고민해보기
    }

    public void doService(InputStream in, OutputStream out) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // request
        HttpRequest request = HttpRequest.from(in);
        logger.debug("{}", request);

        doDispatch(request, out);
    }
}
