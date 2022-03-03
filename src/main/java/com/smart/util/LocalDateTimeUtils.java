package com.smart.util;

import cn.hutool.core.util.ReUtil;
import com.smart.contant.enums.SmartExceptionEnum;
import com.smart.exception.SmartException;
import org.apache.commons.lang3.StringUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * @author ken
 * @description localDateTime工具类
 */
public class LocalDateTimeUtils {
    private LocalDateTimeUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static final String FORMATTER_SECOND = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMATTER_MINUTE = "yyyy-MM-dd HH:mm";
    public static final String FORMATTER_MILLISECOND = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String FORMATTER_DATE = "yyyy-MM-dd";
    public static final String FORMATTER_DATE_YYYYMMDD = "yyyyMMdd";

    private static Pattern PATTERN_REGEX_MINUTE = Pattern.compile("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$");
    private static Pattern PATTERN_REGEX_DAY = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
    private static Pattern PATTERN_REGEX_SECOND = Pattern.compile("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$");
    private static Pattern PATTERN_REGEX_MILLISECOND = Pattern.compile("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}\\.\\d{3}$");

    public static String format(LocalDateTime localDateTime) {
        return format(localDateTime, FORMATTER_SECOND);
    }

    public static String format(LocalDate localDate) {
        String result = "";
        if (localDate != null) {
            result = localDate.toString();
        }
        return result;
    }

    public static String format(LocalDateTime localDateTime, String formatter) {
        String result = "";
        if (localDateTime != null) {
            result = localDateTime.format(DateTimeFormatter.ofPattern(formatter));
        }
        return result;
    }

    public static LocalDateTime parse(String localDateTimeStr) {
        if (StringUtils.isEmpty(localDateTimeStr)) {
            return null;
        }
        String formatter = "";
        if(ReUtil.isMatch(PATTERN_REGEX_SECOND,localDateTimeStr)){
            formatter = FORMATTER_SECOND;
        }else if (ReUtil.isMatch(PATTERN_REGEX_MINUTE,localDateTimeStr)) {
            formatter = FORMATTER_MINUTE;
        } else if (ReUtil.isMatch(PATTERN_REGEX_MILLISECOND,localDateTimeStr)) {
            formatter = FORMATTER_MILLISECOND;
        } else if (ReUtil.isMatch(PATTERN_REGEX_DAY,localDateTimeStr)) {
            formatter = FORMATTER_DATE;
        }
        if (StringUtils.isNotBlank(formatter)) {
            return parse(localDateTimeStr, formatter);
        } else {
            throw SmartException.throwException(SmartExceptionEnum.DATE_FORMAT,localDateTimeStr);
        }
    }

    public static LocalDate parseLocalDate(String localDateTimeStr) throws SmartException {
        if (StringUtils.isEmpty(localDateTimeStr)) {
            return null;
        }
        String formatter = "";
        if (PATTERN_REGEX_SECOND.matcher(localDateTimeStr).matches()) {
            formatter = FORMATTER_SECOND;
        } else if (PATTERN_REGEX_MINUTE.matcher(localDateTimeStr).matches()) {
            formatter = FORMATTER_MINUTE;
        } else if (PATTERN_REGEX_MILLISECOND.matcher(localDateTimeStr).matches()) {
            formatter = FORMATTER_MILLISECOND;
        } else if (PATTERN_REGEX_DAY.matcher(localDateTimeStr).matches()) {
            formatter = FORMATTER_DATE;
        }
        if (StringUtils.isNotBlank(formatter)) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern(formatter);
            return LocalDate.parse(localDateTimeStr, df);
        } else {
            throw SmartException.throwException(SmartExceptionEnum.DATE_FORMAT,localDateTimeStr);
        }
    }

    public static LocalDateTime parse(String localDateTimeStr, String formatter) {
        LocalDateTime localDateTime = null;
        if (StringUtils.isNotBlank(localDateTimeStr)) {
            localDateTime = LocalDateTime.parse(localDateTimeStr, DateTimeFormatter.ofPattern(formatter));
        }
        return localDateTime;
    }

    public static LocalDateTime getFirstDayOfMonth(LocalDateTime localDateTime) {
        return localDateTime.withDayOfMonth(1);
    }

    public static LocalDateTime getLastDayOfMonth(LocalDateTime localDateTime) {
        return localDateTime.withDayOfMonth(localDateTime.toLocalDate().lengthOfMonth());
    }

    public static LocalDateTime parseInstant(Long instant) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(instant), ZoneOffset.of("+8"));
    }

    public static String getThisMonthTillNowStr() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = LocalDateTimeUtils.getFirstDayOfMonth(today.atStartOfDay()).toLocalDate();
        return firstDayOfMonth.toString() + " - " + today.toString();
    }

    public static Long parseInstant() {
        return LocalDateTime.now().toInstant(ZoneOffset.ofHours(8)).getEpochSecond();
    }

    /**
     * 时间相差 天
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static Long betweenDays(LocalDateTime startTime, LocalDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        return Math.abs(duration.toDays());
    }

    /**
     * 校验时间相差 是否超出 days 天
     *
     * @param startTime
     * @param endTime
     * @param days
     */
    public static void thanDays(String startTime, String endTime, long days) throws SmartException {
        Long aLong = betweenDays(parse(startTime), parse(endTime));
        if (aLong.compareTo(days) > 0) {
        }
    }

}
