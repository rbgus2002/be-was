package webserver.myframework.view;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class StaticView implements View {
    private final File html;

    public StaticView(File html) {
        this.html = html;
    }

    @Override
    public byte[] render() throws IOException {
        return Files.readAllBytes(html.toPath());
    }
}
