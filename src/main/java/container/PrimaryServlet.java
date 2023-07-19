package container;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;
import java.io.DataOutputStream;
import java.io.IOException;

public class PrimaryServlet implements Servlet {

    private static final Logger logger = LoggerFactory.getLogger(Servlet.class);

    @Override
    public void service(HTTPServletRequest request, HTTPServletResponse response, DataOutputStream dos) throws IOException {
        dos.writeBytes(response.getHeader());
        dos.write(response.getBody());
    }


}
