package view.view;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public interface View {
    void render(Map<String, Object> model, DataOutputStream dos) throws IOException;
}
