package webserver.myframework.view;

import webserver.myframework.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class StaticView implements View {
    private final File viewFile;

    public StaticView(File viewFile) {
        this.viewFile = viewFile;
    }

    @Override
    public String getFileExtension() {
        return FileUtils.getExtension(viewFile);
    }

    @Override
    public byte[] render() throws IOException {
        return Files.readAllBytes(viewFile.toPath());
    }
}
