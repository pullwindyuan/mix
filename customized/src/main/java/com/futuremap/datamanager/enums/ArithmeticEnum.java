package com.futuremap.datamanager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * 运算规则枚举，为了规范描述，采用枚举来定义，并且使用的BigDecimal处理运算
 * @author dell
 */
@Getter
@AllArgsConstructor
public enum ArithmeticEnum {
    /**
     *
     */
    ADD(1000, "加", "对数据聚合求平均值"),
    SUB(1100, "减", "对数据聚合求和"),
    MULTI(1200, "乘", "对数据进行分类"),
    DIV(1300, "除", "对数据进行排序"),
    SCALE(1400, "精度控制", "保留多少位数"),
    ABS(1500, "绝对值", "绝对值"),
    HALF_UP(BigDecimal.ROUND_HALF_UP, "入", "参照RoundingMode的标准定义"),
    HALF_DOWN(BigDecimal.ROUND_HALF_DOWN, "舍", "参照RoundingMode的标准定义"),
    HALF_EVEN(BigDecimal.ROUND_HALF_EVEN, "平分", "参照RoundingMode的标准定义"),
    CALENDAR_YEAR_ADD(2000, "日历年相加", "两个日历年相加"),
    CALENDAR_YEAR_ADD_CONSTANT(2001, "日历年加常数", "一个日历年加一个年常数"),
    CALENDAR_MONTH_ADD(2100, "日历月相加", "两个日历月相加"),
    CALENDAR_MONTH_ADD_CONSTANT(2101, "日历月加常数", "一个日历月加一个月常数"),
    CALENDAR_DAY_ADD(2200, "日历日相加", "两个日历日相加"),
    CALENDAR_DAY_ADD_CONSTANT(2201, "日历日加常数", "一个日历日加一个日常数"),
    CALENDAR_HOUR_ADD(2300, "日历小时相加", "两个日历小时相加"),
    CALENDAR_HOUR_ADD_CONSTANT(2301, "日历小时加常数", "一个日历小时加一个小时常数"),
    CALENDAR_MINUTE_ADD(2400, "日历分钟相加", "两个日历分钟相加"),
    CALENDAR_MINUTE_ADD_CONSTANT(2401, "日历分钟加常数", "一个日历分钟加一个分钟常数"),
    CALENDAR_SECOND_ADD(2500, "日历秒相加", "两个日历秒相加"),
    CALENDAR_SECOND_ADD_CONSTANT(2501, "日历秒加常数", "一个日历秒加一个秒常数"),
    CALENDAR_WEEK_ADD(2600, "日历周相加", "两个日历周相加"),
    CALENDAR_WEEK_ADD_CONSTANT(2601, "日历周加常数", "一个日历周加一个周常数"),

    CALENDAR_YEAR_SUB(3000, "日历年相减", "两个日历年相减"),
    CALENDAR_YEAR_SUB_CONSTANT(3001, "日历年减常数", "一个日历年减一个年常数"),
    CALENDAR_MONTH_SUB(3100, "日历月相减", "两个日历月相减"),
    CALENDAR_MONTH_SUB_CONSTANT(3101, "日历月减常数", "一个日历月减一个月常数"),
    CALENDAR_DAY_SUB(3200, "日历日相减", "两个日历日相减"),
    CALENDAR_DAY_SUB_CONSTANT(3201, "日历日减常数", "一个日历日减一个日常数"),
    CALENDAR_HOUR_SUB(3300, "日历小时相减", "两个日历小时相减"),
    CALENDAR_HOUR_SUB_CONSTANT(3301, "日历小时减常数", "一个日历小时减一个小时常数"),
    CALENDAR_MINUTE_SUB(3400, "日历分钟相减", "两个日历分钟相减"),
    CALENDAR_MINUTE_SUB_CONSTANT(3401, "日历分钟减常数", "一个日历分钟减一个分钟常数"),
    CALENDAR_SECOND_SUB(3500, "日历秒相减", "两个日历秒相减"),
    CALENDAR_SECOND_SUB_CONSTANT(3501, "日历秒减常数", "一个日历秒减一个秒常数"),
    CALENDAR_WEEK_SUB(3600, "日历周相减", "两个日历周相减"),
    CALENDAR_WEEK_SUB_CONSTANT(3601, "日历周减常数", "一个日历周减一个周常数"),

//    CALENDAR_YEAR_MULTI(2000, "日历年相乘", "两个日历年相乘"),
//    CALENDAR_YEAR_MULTI_CONSTANT(2001, "日历年乘常数", "一个日历年乘一个年常数"),
//    CALENDAR_MONTH_MULTI(2100, "日历月相乘", "两个日历月相乘"),
//    CALENDAR_MONTH_MULTI_CONSTANT(2101, "日历月乘常数", "一个日历月乘一个月常数"),
//    CALENDAR_DAY_MULTI(2200, "日历日相乘", "两个日历日相乘"),
//    CALENDAR_DAY_MULTI_CONSTANT(2201, "日历日乘常数", "一个日历日乘一个日常数"),
//    CALENDAR_HOUR_MULTI(2300, "日历小时相乘", "两个日历小时相乘"),
//    CALENDAR_HOUR_MULTI_CONSTANT(2301, "日历小时乘常数", "一个日历小时乘一个小时常数"),
//    CALENDAR_MINUTE_MULTI(2400, "日历分钟相乘", "两个日历分钟相乘"),
//    CALENDAR_MINUTE_MULTI_CONSTANT(2401, "日历分钟乘常数", "一个日历分钟乘一个分钟常数"),
//    CALENDAR_SECOND_MULTI(2500, "日历秒相乘", "两个日历秒相乘"),
//    CALENDAR_SECOND_MULTI_CONSTANT(2501, "日历秒乘常数", "一个日历秒乘一个秒常数"),
//    CALENDAR_WEEK_MULTI(2600, "日历周相乘", "两个日历周相乘"),
//    CALENDAR_WEEK_MULTI_CONSTANT(2601, "日历周乘常数", "一个日历周乘一个周常数"),
//
//    CALENDAR_YEAR_DIV(2000, "日历年相除", "两个日历年相除"),
//    CALENDAR_YEAR_DIV_CONSTANT(2001, "日历年除常数", "一个日历年除一个年常数"),
//    CALENDAR_MONTH_DIV(2100, "日历月相除", "两个日历月相除"),
//    CALENDAR_MONTH_DIV_CONSTANT(2101, "日历月除常数", "一个日历月除一个月常数"),
//    CALENDAR_DAY_DIV(2200, "日历日相除", "两个日历日相除"),
//    CALENDAR_DAY_DIV_CONSTANT(2201, "日历日除常数", "一个日历日除一个日常数"),
//    CALENDAR_HOUR_DIV(2300, "日历小时相除", "两个日历小时相除"),
//    CALENDAR_HOUR_DIV_CONSTANT(2301, "日历小时除常数", "一个日历小时除一个小时常数"),
//    CALENDAR_MINUTE_DIV(2400, "日历分钟相除", "两个日历分钟相除"),
//    CALENDAR_MINUTE_DIV_CONSTANT(2401, "日历分钟除常数", "一个日历分钟除一个分钟常数"),
//    CALENDAR_SECOND_DIV(2500, "日历秒相除", "两个日历秒相除"),
//    CALENDAR_SECOND_DIV_CONSTANT(2501, "日历秒除常数", "一个日历秒除一个秒常数"),
//    CALENDAR_WEEK_DIV(2600, "日历周相乘", "两个日历周相乘"),
//    CALENDAR_WEEK_DIV_CONSTANT(2601, "日历周除常数", "一个日历周除一个周常数"),

    CALENDAR_COUNT_BY_YEAR(1500, "绝对值", "绝对值"),
    CALENDAR_COUNT_BY_MONTH(1500, "绝对值", "绝对值"),
    CALENDAR_COUNT_BY_DAY(1500, "绝对值", "绝对值"),
    CALENDAR_COUNT_BY_WEEK(1500, "绝对值", "绝对值"),
    CALENDAR_COUNT_BY_HOUR(1500, "绝对值", "绝对值"),
    CALENDAR_COUNT_BY_MINUTE(1500, "绝对值", "绝对值"),
    CALENDAR_COUNT_BY_SECOND(1500, "绝对值", "绝对值"),

    CALENDAR_COUNTDOWN_BY_YEAR(1500, "绝对值", "绝对值"),
    CALENDAR_COUNTDOWN_BY_MONTH(1500, "绝对值", "绝对值"),
    CALENDAR_COUNTDOWN_BY_DAY(1500, "绝对值", "绝对值"),
    CALENDAR_COUNTDOWN_BY_WEEK(1500, "绝对值", "绝对值"),
    CALENDAR_COUNTDOWN_BY_HOUR(1500, "绝对值", "绝对值"),
    CALENDAR_COUNTDOWN_BY_MINUTE(1500, "绝对值", "绝对值"),
    CALENDAR_COUNTDOWN_BY_SECOND(1500, "绝对值", "绝对值"),

    CALENDAR_YEAR_COUNT_BY_YEAR(1500, "绝对值", "绝对值"),
    CALENDAR_YEAR_COUNT_BY_MONTH(1500, "绝对值", "绝对值"),
    CALENDAR_YEAR_COUNT_BY_DAY(1500, "绝对值", "绝对值"),
    CALENDAR_YEAR_COUNT_BY_WEEK(1500, "绝对值", "绝对值"),
    CALENDAR_YEAR_COUNT_BY_HOUR(1500, "绝对值", "绝对值"),
    CALENDAR_YEAR_COUNT_BY_MINUTE(1500, "绝对值", "绝对值"),
    CALENDAR_YEAR_COUNT_BY_SECOND(1500, "绝对值", "绝对值"),

    CALENDAR_YEAR_COUNTDOWN_BY_YEAR(1500, "绝对值", "绝对值"),
    CALENDAR_YEAR_COUNTDOWN_BY_MONTH(1500, "绝对值", "绝对值"),
    CALENDAR_YEAR_COUNTDOWN_BY_DAY(1500, "绝对值", "绝对值"),
    CALENDAR_YEAR_COUNTDOWN_BY_WEEK(1500, "绝对值", "绝对值"),
    CALENDAR_YEAR_COUNTDOWN_BY_HOUR(1500, "绝对值", "绝对值"),
    CALENDAR_YEAR_COUNTDOWN_BY_MINUTE(1500, "绝对值", "绝对值"),
    CALENDAR_YEAR_COUNTDOWN_BY_SECOND(1500, "绝对值", "绝对值"),

    CALENDAR_CURRENT_YEAR_COUNT_BY_MONTH(1500, "绝对值", "绝对值"),
    CALENDAR_CURRENT_YEAR_COUNT_BY_DAY(1500, "绝对值", "绝对值"),
    CALENDAR_CURRENT_YEAR_COUNT_BY_WEEK(1500, "绝对值", "绝对值"),
    CALENDAR_CURRENT_YEAR_COUNT_BY_HOUR(1500, "绝对值", "绝对值"),
    CALENDAR_CURRENT_YEAR_COUNT_BY_MINUTE(1500, "绝对值", "绝对值"),
    CALENDAR_CURRENT_YEAR_COUNT_BY_SECOND(1500, "绝对值", "绝对值"),

    CALENDAR_CURRENT_YEAR_COUNTDOWN_BY_MONTH(1500, "绝对值", "绝对值"),
    CALENDAR_CURRENT_YEAR_COUNTDOWN_BY_DAY(1500, "绝对值", "绝对值"),
    CALENDAR_CURRENT_YEAR_COUNTDOWN_BY_WEEK(1500, "绝对值", "绝对值"),
    CALENDAR_CURRENT_YEAR_COUNTDOWN_BY_HOUR(1500, "绝对值", "绝对值"),
    CALENDAR_CURRENT_YEAR_COUNTDOWNBY_MINUTE(1500, "绝对值", "绝对值"),
    CALENDAR_CURRENT_YEAR_COUNTDOWN_BY_SECOND(1500, "绝对值", "绝对值"),

    NONE(99, "无", "无");


    private final Integer code;
    private final String name;
    private final String desc;
}
