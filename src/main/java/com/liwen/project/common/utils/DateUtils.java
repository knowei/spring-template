package com.liwen.project.common.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 时间工具类
 */
public class DateUtils {
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public DateUtils() {
    }

    public static String getCurrentTimeString() {
        LocalDateTime now = LocalDateTime.now();
        return dateTimeFormatter.format(now);
    }
}
