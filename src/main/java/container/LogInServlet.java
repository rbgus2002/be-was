package container;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HTTPServletRequest;
import webserver.HTTPServletResponse;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class LogInServlet implements Servlet {

    private static final Logger logger = LoggerFactory.getLogger(LogInServlet.class);

    @Override
    public void service(HTTPServletRequest request, HTTPServletResponse response, DataOutputStream dos) throws IOException {
        ConcurrentHashMap<String, String> query = request.getQuery();
        User user = new User(query.get("userId"), query.get("password"), query.get("name"), query.get("email"));
        logger.debug("user = {}", user.toString());
        Database.addUser(user);
        dos.writeBytes(response.getHeader());
    }
}
