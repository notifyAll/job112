package com.qgy;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    /**
     * 获得时间字符串
     * @param pattern
     * @param amountDay 需要修改的天  比如3天前的日期 -3
     * @return
     */
    public static String getStringTime(String pattern,int amountDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, amountDay);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String format = simpleDateFormat.format(calendar.getTime());
        return format;
    }

    public static String getStringTime(String pattern) {
        return getStringTime(pattern,0);
    }
}
