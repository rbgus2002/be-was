package common.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.stream.Collectors;

public class FileUtil {
    public static byte[] get(final URL resource) throws IOException {
        if (resource == null) {
            return null;
        }
        String file = resource.getFile();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            return bufferedReader.lines()
                    .collect(Collectors.joining())
                    .getBytes();
        }
    }
}
