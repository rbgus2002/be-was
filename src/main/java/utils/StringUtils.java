package utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class StringUtils {

    public static String appendLineSeparator(String line){
        return line + System.lineSeparator();
    }

    public static String getDecodedString(String encodedString){
        try {
            return URLDecoder.decode(encodedString, "UTF-8");
        }
        catch (UnsupportedEncodingException e){
            return encodedString;
        }
    }
}
