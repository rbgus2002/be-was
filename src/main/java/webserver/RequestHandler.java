package webserver;

import container.DispatcherServlet;
import db.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.ParserFactory;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

import static container.DispatcherServlet.getInstance;

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
            Database.initialize();

            HTTPServletRequest request = parserFactory.createParser(startLine.split(" ")[0]).getProperRequest(startLine, br);
            DispatcherServlet dispatcherServlet = getInstance();
            logger.debug("dispatcherServlet = {}", dispatcherServlet);
            HTTPServletResponse response = new HTTPServletResponse(dos);
            dispatcherServlet.service(request, response);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (InvocationTargetException e) {
            logger.debug(e.getMessage());
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            logger.debug(e.getMessage());
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
