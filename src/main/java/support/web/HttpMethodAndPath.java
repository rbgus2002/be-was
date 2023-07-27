package support.web;

import com.google.common.base.Objects;

public class HttpMethodAndPath {

    private final HttpMethod httpMethod;
    private final String path;

    public HttpMethodAndPath(HttpMethod httpMethod, String path) {
        this.httpMethod = httpMethod;
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HttpMethodAndPath that = (HttpMethodAndPath) o;
        return httpMethod == that.httpMethod && Objects.equal(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(httpMethod, path);
    }

}
