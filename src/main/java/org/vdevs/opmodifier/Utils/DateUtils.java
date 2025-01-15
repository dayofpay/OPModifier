package org.vdevs.opmodifier.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public static String getCurrentDate() {
        LocalDateTime now = LocalDateTime.now();
        return DEFAULT_FORMATTER.format(now);
    }
}
