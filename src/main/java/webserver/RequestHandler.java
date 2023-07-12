package webserver;

import java.io.*;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());


        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            DataOutputStream dos = new DataOutputStream(out);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String request = br.readLine();
            logger.debug("request : " + request);
            HttpRequest httpRequest = new HttpRequest(request);
            DispatcherServlet dispatcherServlet = new DispatcherServlet();

            dispatcherServlet.service(httpRequest, new HttpResponse(dos));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }




}
