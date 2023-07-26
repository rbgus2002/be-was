package utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class GMTStringConverter {
    public static String convertToGMTString(LocalDateTime localDateTime) {
        ZonedDateTime gmtDateTime = localDateTime.atZone(ZoneId.of("GMT"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.ENGLISH);
        return gmtDateTime.format(formatter);
    }
}
