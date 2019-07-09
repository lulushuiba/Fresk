package com.asterism.fresk.util;

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
}
