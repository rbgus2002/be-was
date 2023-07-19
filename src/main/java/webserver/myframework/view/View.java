package webserver.myframework.view;

import java.io.IOException;

public interface View {
    byte[] render() throws IOException;
}
