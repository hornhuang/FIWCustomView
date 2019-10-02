package com.fishinwater.fiwentertainmentstar.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.reactivex.annotations.NonNull;

public class Dater {

    private static SimpleDateFormat format;

    public static int getDiscrepantDays(@NonNull Date dateStart, @NonNull Date dateEnd) {
        return (int) ((dateEnd.getTime() - dateStart.getTime()) / 1000 / 60 / 60 / 24);
    }

    public static String getYMDString(Date date){
        format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String getYMRHMSString(Date date){
        format = new SimpleDateFormat("yyyy-MM-dd-HH-MM-SS");
        return format.format(date);
    }

    public static Date getDateByYMDString(String dateString){
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(dateString);
        }
        catch (ParseException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 获得当前日期
     */
    public static int getWeekOfDate() {
        Date dt = new Date();
        int[] weekDays = {7, 1, 2, 3, 4, 5, 6};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0){
            w = 0;
        }
        return weekDays[w];
    }

    /**
     * 比较量日期是否相等，忽略时分秒
     */
    public static boolean sameDate(Date d1, Date d2){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        //fmt.setTimeZone(new TimeZone()); // 如果需要设置时间区域，可以在这里设置
        return fmt.format(d1).equals(fmt.format(d2));
    }
}
