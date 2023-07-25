package webserver.http;

import webserver.http.message.HttpHeaders;
import webserver.http.message.HttpResponse;
import webserver.http.message.HttpStatus;
import webserver.http.message.HttpVersion;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class HttpResponseSender {
    public static final String SPACE = " ";
    public static final String CRLF = "\r\n";
    public static final String COLON = ":";


    public void sendResponse(OutputStream outputStream, HttpResponse httpResponse) throws IOException {
        DataOutputStream dos = new DataOutputStream(outputStream);

        writeStatusLine(dos, httpResponse);
        writeHeaderLine(dos, httpResponse);
        writeBlankLine(dos);
        writeMessageBody(dos, httpResponse);

        dos.flush();
    }

    private void writeMessageBody(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        if (httpResponse.getBody().length != 0) {
            write(dos, httpResponse.getBody());
        }
    }

    private void writeBlankLine(DataOutputStream dos) throws IOException {
        write(dos, CRLF.getBytes());
    }

    private void writeHeaderLine(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        byte[] headerLines = makeHeaderLines(httpResponse);
        write(dos, headerLines);
    }

    private void writeStatusLine(DataOutputStream dos, HttpResponse httpResponse) throws IOException {
        byte[] statusLine = makeStatusLine(httpResponse);
        write(dos, statusLine);
    }

    private void write(DataOutputStream dos, byte[] statusLine) throws IOException {
        dos.write(statusLine);
    }

    private byte[] makeHeaderLines(HttpResponse httpResponse) {
        HttpHeaders httpHeaders = httpResponse.getHttpHeaders();
        Map<String, List<String>> headers = httpHeaders.getHeaders();

        StringBuilder headerLineBuilder = new StringBuilder();
        for (String key : headers.keySet()) {
            String values = String.join(SPACE, headers.get(key));
            headerLineBuilder
                    .append(key).append(COLON).append(SPACE)
                    .append(values).append(CRLF);
        }
        return headerLineBuilder.toString().getBytes();
    }

    private byte[] makeStatusLine(HttpResponse httpResponse) {
        StringBuilder statusLineBuilder = new StringBuilder();

        HttpVersion httpVersion = httpResponse.getHttpVersion();
        HttpStatus httpStatus = httpResponse.getHttpStatus();

        statusLineBuilder.append(httpVersion.getVersion()).append(SPACE);
        statusLineBuilder.append(httpStatus.getStatusCode()).append(SPACE);
        statusLineBuilder.append(httpStatus.getReasonPhrase()).append(CRLF);

        return statusLineBuilder.toString().getBytes();
    }
}
