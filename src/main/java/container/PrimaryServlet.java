package container;

import creator.AcceptCreator;
import creator.Creator;
import creator.CreatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;
import java.io.DataOutputStream;
import java.io.IOException;

public class PrimaryServlet implements Servlet {

    private static final Logger logger = LoggerFactory.getLogger(Servlet.class);

    @Override
    public void service(HTTPServletRequest request, HTTPServletResponse response) throws IOException {
        CreatorFactory factory = new CreatorFactory();

        response = new AcceptCreator().getProperResponse(request);
        DataOutputStream writer = response.getWriter();
        writer.writeBytes(response.getHeader());
        writer.write(response.getBody());
    }


}
