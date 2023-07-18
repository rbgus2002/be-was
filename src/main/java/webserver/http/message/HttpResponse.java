package webserver.http.message;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {

	private static final Logger logger = LoggerFactory.getLogger(HttpResponse.class);

	private final DataOutputStream dos;

	private HttpResponse(DataOutputStream dos) {
		this.dos = dos;
	}

	public static HttpResponse from(OutputStream outputStream) {
		return new HttpResponse(new DataOutputStream(outputStream));
	}

	public void writeResponse200Header(int lengthOfBodyContent) {
		try {
			dos.writeBytes("HTTP/1.1 200 OK \r\n");
			dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
			dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public void writeResponseBody(byte[] body) {
		try {
			dos.write(body, 0, body.length);
			dos.flush();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

}
