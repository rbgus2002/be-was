package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtils {

    public static byte[] readFileToBytes(String filePath) {
        byte[] bytes;

        try {
            bytes = Files.readAllBytes(new File(filePath).toPath());
        } catch (IOException e) {
            return null;
        }

        return bytes;
    }

    public static String readFileToString(String filePath) {
        String string;

        try {
            string = Files.readString(new File(filePath).toPath());
        } catch (IOException e) {
            return null;
        }

        return string;
    }

}
