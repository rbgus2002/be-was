package webserver.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileNameScanner {
    private static final Logger logger = LoggerFactory.getLogger(FileNameScanner.class);

    private FileNameScanner() {
    }

    public static List<String> scan(String pathString) {
        try (Stream<Path> paths = Files.walk(Paths.get(pathString))) {
            return paths.filter(Files::isRegularFile)
                    .map(Path::toString)
                    .map(path -> path.replace(pathString, ""))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error(e.getMessage());
            return List.of();
        }
    }
}
