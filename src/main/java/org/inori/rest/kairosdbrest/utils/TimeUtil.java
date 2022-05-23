package org.inori.rest.kairosdbrest.utils;

import lombok.extern.log4j.Log4j2;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.ResolverStyle;
import java.util.Date;
import java.util.Locale;

/**
 * @author InoriHimea
 * @version 1.0
 * @date 2021/10/5 16:10
 * @since 1.8
 */
@Log4j2
public final class TimeUtil {

    /**
     * 获取当前时间的默认格式化信息，根据服务器当前时区
     *
     * @return
     */
    public static String getFormatNowTime() {
        return LocalDateTime.now().atZone(ZoneId.of("Asia/Shanghai")).format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.FULL));
    }

    public static String getNowTimeWithPattern(String pattern) {
        return LocalDateTime.now().atZone(ZoneId.of("Asia/Shanghai")).format(DateTimeFormatter.ofPattern(pattern, Locale.CHINA));
    }

    public static String formatEpochMilli(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
                .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.FULL)
                        .withLocale(Locale.CHINA)
                        .withResolverStyle(ResolverStyle.STRICT)
                        .withZone(ZoneId.systemDefault()));
    }

    public static String formatEpochMilli2Custom(long timestamp, String pattern) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern(pattern)
                        .withLocale(Locale.CHINA)
                        .withResolverStyle(ResolverStyle.STRICT)
                        .withZone(ZoneId.systemDefault()));
    }

    public static void main(String[] args) {
        System.out.println(TimeUtil.getNowTimeWithPattern("yyyy年MM月dd日HH时mm分ss秒"));
    }

    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static Date isDate(String date){
        try{
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return fmt.parse(date);
        }catch(java.text.ParseException e){
            return null;
        }
    }
}
