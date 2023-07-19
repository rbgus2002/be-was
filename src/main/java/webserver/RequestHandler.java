package webserver;

import container.DispatcherServlet;
import container.Servlet;
import creator.AcceptCreator;
import creator.CreatorFactory;
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
            CreatorFactory creatorFactory = new CreatorFactory();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            DataOutputStream dos = new DataOutputStream(out);
            String startLine = br.readLine();
            logger.debug("startLine = {}", startLine);
            HTTPServletRequest request = parserFactory.createParser(startLine.split(" ")[0]).getProperRequest(startLine, br);
            Servlet servlet = DispatcherServlet.findServlet(request);
            HTTPServletResponse response = creatorFactory.createCreator(servlet).getProperResponse(request);
            servlet.service(request, response, dos);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
