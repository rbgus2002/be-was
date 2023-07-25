package webserver.container;

public class EndPoint {
    String path;
    String method;

    public EndPoint(String path, String method) {
        this.path = path;
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }

    // todo: hashCode/equals Overriding -> cmd+N
    
    @Override
    public int hashCode() {
        int h = 0;
        for (char character : path.concat(method).toCharArray()) {
            h += character;
        }
        return h;
    }
    

    @Override
    public boolean equals(Object obj) {
        return obj.hashCode() == this.hashCode();
    }
}
