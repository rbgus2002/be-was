package webserver.myframework.view;

import java.io.File;
import java.io.IOException;

public interface View {
    String getFileExtension();
    byte[] render() throws IOException;
}
