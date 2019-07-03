package com.asterism.fresk.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String getNowToString(){
        // 设置时间格式
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd hh:mm");
        return formatter.format(new Date());
    }
}
