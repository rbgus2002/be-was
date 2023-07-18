package webserver.view;


public interface ViewResolver {
    View resolve(String viewPath);
}
