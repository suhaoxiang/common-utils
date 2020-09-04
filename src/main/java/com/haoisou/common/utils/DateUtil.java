package com.haoisou.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static final SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
    private static final SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public DateUtil() {
    }

    public static String getYear() {
        return sdfYear.format(new Date());
    }

    public static String getDay() {
        return sdfDay.format(new Date());
    }

    public static String getDays() {
        return sdfDays.format(new Date());
    }

    public static String getTime() {
        return sdfTime.format(new Date());
    }

    public static boolean compareDate(String s, String e) {
        if (fomatDate(s) != null && fomatDate(e) != null) {
            return fomatDate(s).getTime() >= fomatDate(e).getTime();
        } else {
            return false;
        }
    }

    public static Date fomatDate(String date) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

        try {
            return fmt.parse(date);
        } catch (ParseException var3) {
            return null;
        }
    }

    public static boolean isValidDate(String s) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

        try {
            fmt.parse(s);
            return true;
        } catch (Exception var3) {
            return false;
        }
    }

    public static int getDiffYear(String startTime, String endTime) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

        try {
            long aa = 0L;
            int years = (int)((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime()) / 86400000L / 365L);
            return years;
        } catch (Exception var6) {
            return 0;
        }
    }

    public static long getDaySub(String beginDateStr, String endDateStr) {
        long day = 0L;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;

        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
        } catch (ParseException var8) {
            var8.printStackTrace();
        }

        day = (endDate.getTime() - beginDate.getTime()) / 86400000L;
        return day;
    }

    public static String getAfterDayDate(String days) {
        int daysInt = Integer.parseInt(days);
        Calendar canlendar = Calendar.getInstance();
        canlendar.add(5, daysInt);
        Date date = canlendar.getTime();
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);
        return dateStr;
    }

    public static String getAfterDayWeek(String days) {
        int daysInt = Integer.parseInt(days);
        Calendar canlendar = Calendar.getInstance();
        canlendar.add(5, daysInt);
        Date date = canlendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    public static void main(String[] args) {
        System.out.println(getDays());
        System.out.println(getAfterDayWeek("3"));
    }
}
