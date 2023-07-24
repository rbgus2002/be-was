package webserver;

import container.DispatcherServlet;
import container.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.ParserFactory;

import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;



    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(), connection.getPort());
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            ParserFactory parserFactory = new ParserFactory();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            DataOutputStream dos = new DataOutputStream(out);
            String startLine = br.readLine();
            logger.debug("startLine = {}", startLine);
            
            HTTPServletRequest request = parserFactory.createParser(startLine.split(" ")[0]).getProperRequest(startLine, br);
            DispatcherServlet dispatcherServlet = new DispatcherServlet();
            Controller controller = dispatcherServlet.findServlet(request);
            HTTPServletResponse response = new HTTPServletResponse(dos);
            
            logger.debug("servlet = {}", controller);
            controller.process(request, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
