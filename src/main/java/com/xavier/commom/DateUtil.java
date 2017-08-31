package com.xavier.commom;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * 基于Java8全新时间类的时间相关的工具
 * @author zhengwei
 * @create 2017-07-17 17:48
 */
public class DateUtil {

    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_PATTERN);

    public static String getStringDate(long datetime, String pattern) {
        LocalDateTime dateTime = toLocalDateTime(datetime);
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(customFormatter);
    }

    public static String getStringDate(long datetime) {
        LocalDateTime dateTime = toLocalDateTime(datetime);
        return dateTime.format(DEFAULT_FORMATTER);
    }

    public static String getStringDate(java.util.Date date) {
        LocalDateTime dateTime = toLocalDateTime(date);
        return dateTime.format(DEFAULT_FORMATTER);
    }

    public static String getStringDate(java.sql.Date date) {
        LocalDateTime dateTime = toLocalDateTime(date);
        return dateTime.format(DEFAULT_FORMATTER);
    }

    /// convert to LocalDateTime
    public static LocalDateTime toLocalDateTime(long datetime) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(datetime), ZoneId.systemDefault());
    }

    public static LocalDateTime toLocalDateTime(java.util.Date date) {
        Instant instant = date.toInstant();
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static LocalDateTime toLocalDateTime(java.sql.Date date) {
        Instant instant = date.toInstant();
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static LocalDateTime toLocalDateTime(String datetimeStr, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(datetimeStr, formatter);
    }
    /// convert to LocalDateTime

    public static LocalDateTime toDefaultLocalDateTime(String datetimeStr) {
        return LocalDateTime.parse(datetimeStr, DEFAULT_FORMATTER);
    }



    public static String genCurrentDateTime() {
        return LocalDateTime.now().format(DEFAULT_FORMATTER);
    }
}
