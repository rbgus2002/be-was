package webserver;

import java.io.*;
import java.net.Socket;

import container.Servlet;
import container.DispatcherServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.GetParser;
import parser.ParserFactory;
import parser.PostParser;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    private ParserFactory factory;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            factory = new ParserFactory();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            DataOutputStream dos = new DataOutputStream(out);
            String line = "";
            String startLine = br.readLine();
            logger.debug("startLine = {}", startLine);
            HTTPServletRequest request = null;
            request = factory.createParser(startLine.split(" ")[0]).getProperRequest(startLine, br);
            Servlet servlet = DispatcherServlet.findServlet(request);
            HTTPServletResponse response = new HTTPServletResponse(dos);
            servlet.service(request, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
