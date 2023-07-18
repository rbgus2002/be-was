package global.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpUtil {
    private static final String HTML_EXTENSION = "html";
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static String getBuffers(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while (bufferedReader.ready()) {
            line = bufferedReader.readLine();
            logger.info("line = {}", line);
            stringBuilder.append(line).append(System.lineSeparator());

            logger.debug("request = {}", line);
        }
        return stringBuilder.toString();
    }

    public static String getUrl(String content) {
        String[] splitResult = content.split(" ");
        return splitResult.length >= 2 ? splitResult[1] : content;
    }

    public static boolean isFileRequest(String url) {
        String[] splitUrl = url.split("[.]");
        return splitUrl.length > 0 && isHtmlExtension(splitUrl[splitUrl.length - 1]);
    }

    private static boolean isHtmlExtension(String extension) {
        return extension.equals(HTML_EXTENSION);
    }

}
