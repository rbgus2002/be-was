package webserver.view;

import java.io.DataOutputStream;
import java.io.IOException;

public interface View {
    byte[] render(DataOutputStream dataOutputStream) throws IOException;
}
