package creator;

import container.LogInServlet;
import container.Servlet;

public class CreatorFactory {
    public Creator createCreator(Servlet servlet) {
        if (servlet instanceof LogInServlet) {
            return new RedirectCreator();
        }

        return new AcceptCreator();
    }
}
