package com.asterism.fresk.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author Ashinch
 * @email Glaxyinfinite@outlook.com
 * @date on 2019-07-06 17:21
 */
public class DateUtils {

    /**
     * 获取现在格式化日期后的字符串
     *
     * @return 返回MM-dd hh:mm格式当前日期字符串
     */
    public static String getNowToString() {
        // 设置日期格式
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd hh:mm");
        return formatter.format(new Date());
    }

    /**
     * 比较两个日期字符串
     *
     * @param s1 作比较的日期字符串
     * @param s2 被比较的日期字符串
     *
     * @return d1小于d2返回-1，d1大于d2返回1，相等返回0
     */
    public static int compareDateByString(String s1, String s2) {
        // 设置日期格式
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd hh:mm");
        Date d1 = new Date();
        Date d2 = new Date();
        try {
            d1 = formatter.parse(s1);
            d2 = formatter.parse(s2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d1.compareTo(d2);
    }
}
