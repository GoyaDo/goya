package com.ysmjjsy.goya.component.common.utils;

import com.ysmjjsy.goya.component.dto.constants.DefaultConstants;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.core.math.NumberUtil;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * <p>Description: 特殊日期处理 </p>
 *
 * @author goya
 * @since 2022/7/2 22:54
 */
public class DateTimeUtils {

    private static final String DEFAULT_TIME_ZONE_NAME = "Asia/Shanghai";

    public static String zonedDateTimeToString(ZonedDateTime zonedDateTime) {
        return zonedDateTimeToString(zonedDateTime, DefaultConstants.DATE_TIME_FORMAT);
    }

    public static String zonedDateTimeToString(ZonedDateTime zonedDateTime, String format) {
        if (ObjectUtils.isNotEmpty(zonedDateTime) && StringUtils.isNotBlank(format)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format).withZone(ZoneId.of(DEFAULT_TIME_ZONE_NAME));
            return zonedDateTime.format(formatter);
        }
        return null;
    }

    public static ZonedDateTime stringToZonedDateTime(String dateString) {
        return stringToZonedDateTime(dateString, DefaultConstants.DATE_TIME_FORMAT);
    }

    public static ZonedDateTime stringToZonedDateTime(String dateString, String format) {
        if (StringUtils.isNotBlank(dateString) && StringUtils.isNotBlank(format)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format).withZone(ZoneId.of(DEFAULT_TIME_ZONE_NAME));
            return ZonedDateTime.parse(dateString, formatter);
        }
        return null;
    }

    /**
     * ZonedDateTime 转换成 Date
     *
     * @param zonedDateTime {@link ZonedDateTime}
     * @return 如果 ZonedDateTime 有值则返回对应的 Date，如果为空则返回 当前日期
     */
    public static Date zonedDateTimeToDate(ZonedDateTime zonedDateTime) {
        if (ObjectUtils.isNotEmpty(zonedDateTime)) {
            return Date.from(zonedDateTime.toInstant());
        }
        return new Date();
    }

    /**
     * Date 转换成  ZonedDateTime
     *
     * @param date {@link Date}
     * @return 如果 Date 有值则返回对应的 ZonedDateTime，如果为空则返回 当前日期
     */
    public static ZonedDateTime dateToZonedDateTime(Date date) {
        if (ObjectUtils.isNotEmpty(date)) {
            return ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        }
        return ZonedDateTime.now();
    }

    /**
     * 时间戳转换为 {@link LocalDateTime}
     *
     * @param timestamp 时间戳 {@link Long}
     * @return {@link LocalDateTime} or null
     */
    public static LocalDateTime toLocalDateTime(long timestamp) {
        if (!NumberUtil.isZero(timestamp)) {
            Instant instant = Instant.ofEpochMilli(timestamp);
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        }
        return null;
    }
}
