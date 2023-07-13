package webserver.view;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class HtmlView implements View {
    private final File html;

    public HtmlView(File html) {
        this.html = html;
    }

    @Override
    public byte[] render(DataOutputStream dataOutputStream) throws IOException {
        return Files.readAllBytes(html.toPath());
    }
}
