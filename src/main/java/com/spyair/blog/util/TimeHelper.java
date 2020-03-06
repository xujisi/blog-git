package com.spyair.blog.util;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @version V1.0
 * @Title:时间工具类
 * @ClassName: com.spyair.blog.util.TimeHelper.java
 * @Description:
 * @Copyright 许集思的Springboot
 * @author: 许集思
 * @date: 2020/2/18 22:26
 */

public class TimeHelper {

    private static SimpleDateFormat _dateFormat;
    private static Date _currentDate;

    public static String date_yyyyddmm() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String nowDay = String.valueOf(Long.parseLong(df.format(System.currentTimeMillis())));
        String dqrq = (nowDay.substring(0, 8)); //获取系统当前日期
        return dqrq;
    }

    private synchronized static String timeFormate(String format) {
        _dateFormat = new SimpleDateFormat(format);
        //获取当前的时间
        try {
            _currentDate = new Date();
        } catch (Exception ex) {
            _currentDate = Calendar.getInstance().getTime();
        }
        return _dateFormat.format(_currentDate);
    }

    /**
     * @Description:获取当前的系统时间格式为yyyyMMddHHmmssSSS
     * @author: 许集思
     * @date: 2020/3/2 0:50
     */
    public static String getCurDateTime_yyyyMMddHHmmssSSS() {
        return timeFormate("yyyyMMddHHmmssSSS");
    }
}
