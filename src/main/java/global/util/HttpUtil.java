package global.util;

import exception.BadRequestException;
import global.constant.Extension;
import global.request.RequestBody;
import global.request.RequestHeader;
import global.request.RequestLine;
import global.request.RequestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import static global.constant.Extension.findExtensionType;

public class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    private static final String NEW_LINE = "\r\n";
    private static final String EMPTY_LINE = "";
    private static final String CONTENT_LENGTH = "Content-Length";

    private final BufferedReader reader;
    private final RequestLine requestLine;
    private final RequestHeader headers;
    private final RequestBody requestBody;

    public HttpUtil(InputStream inputStream) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.requestLine = new RequestLine(extractRequestLine());
        this.headers = new RequestHeader(extractHeaders());
        this.requestBody = new RequestBody(extractRequestBody());
    }

    private String extractRequestLine() throws IOException {
        return reader.readLine();
    }

    private String extractHeaders() throws IOException {
        final StringBuilder headerLines = new StringBuilder();
        String header = null;
        while (!EMPTY_LINE.equals(header)) {
            header = reader.readLine();
            logger.debug("header = {}", header);
            if (Objects.isNull(header)) {
                break;
            }
            headerLines.append(header)
                    .append(NEW_LINE);
        }
        return headerLines.toString();
    }

    private String extractRequestBody() throws IOException {
        if (headers.contains(CONTENT_LENGTH)) {
            int contentLength = Integer.parseInt(headers.get(CONTENT_LENGTH));
            logger.debug("body = {}", contentLength);
            char[] buffer = new char[contentLength];
            // TODO 자바 상수 처리
            reader.read(buffer, 0, contentLength);
            return new String(buffer);
        }
        return EMPTY_LINE;
    }

    public String getResponse() throws IOException {
        final RequestMapper mappingHandler = new RequestMapper(this.requestLine, this.requestBody);
        try {
            return mappingHandler.response();
        } catch (BadRequestException e) {
            logger.info(e.getMessage());
            return "Error!";
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "Error!!";
        }
    }
}
