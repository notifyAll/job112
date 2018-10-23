package com.qgy;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTest {
    @Test
    public void dateTesr(){
        System.out.println(new Date());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYY-MM-dd");
        String format = simpleDateFormat.format(new Date());

        Calendar calendar=simpleDateFormat.getCalendar();
        calendar.add(Calendar.DAY_OF_MONTH,-3);
        System.out.println(format);

        Calendar calendar1=Calendar.getInstance();
        String format1 = simpleDateFormat.format(calendar.getTime());

        System.out.println(calendar.getTime());
        System.out.println(format1);

    }

    @Test
    public void  getTime(){
        String s ="18年10月14日";

        String time = TimeUtil.getStringTime("YY年MM月dd日", -1);
        System.out.println(time);
    }
}
