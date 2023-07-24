package webserver.myframework.view;

import webserver.myframework.model.Model;
import webserver.myframework.utils.FileUtils;
import webserver.myframework.utils.StringUtils;
import webserver.myframework.view.content.DynamicContent;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DynamicView implements View {
    private static final String INTERNAL_SERVER_ERROR_PAGE = "src/main/resources/templates/errors/500.html";
    private final File viewFile;
    private final Model model;

    public DynamicView(File viewFile, Model model) {
        this.viewFile = viewFile;
        this.model = model;
    }

    @Override
    public String getFileExtension() {
        return FileUtils.getExtension(viewFile);
    }

    @Override
    public byte[] render() throws IOException {
        String fileAllContent = Files.readString(viewFile.toPath()).replace("{DYNAMIC_RENDER}", "").trim();
        List<String> fileContents = StringUtils.splitStringByRegex(fileAllContent, "(\\{[^\\}]+\\})");
        StringBuilder stringBuilder = new StringBuilder();

        try {
            writeDynamicFile(stringBuilder, fileContents);
        } catch (Exception exception) {
            return Files.readAllBytes(Path.of(INTERNAL_SERVER_ERROR_PAGE));
        }

        return stringBuilder.toString().getBytes(StandardCharsets.UTF_8);
    }

    private void writeDynamicFile(StringBuilder stringBuilder, List<String> fileContents) throws NoSuchFieldException, IllegalAccessException {
        for (String fileContent : fileContents) {
            if (fileContent.startsWith("{") && fileContent.endsWith("}")) {
                    stringBuilder.append(writeDynamicContent(fileContent));
                continue;
            }
            stringBuilder.append(fileContent);
        }
    }

    private String writeDynamicContent(String fileContent) throws NoSuchFieldException, IllegalAccessException {
        String[] objectContents = fileContent.replaceAll("[{}]", "")
                .split(",");
        for (String objectContent : objectContents) {
            String[] objectAndContent = objectContent.split("::");
            String objectName = objectAndContent[0].trim();
            DynamicContent dynamicContent = DynamicContent.getDynamicContent(model, objectName, objectAndContent[1]);
            if(dynamicContent == null) {
                continue;
            }
            return dynamicContent.render();
        }
        throw new IllegalArgumentException();
    }
}
