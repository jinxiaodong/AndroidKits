package com.common.library.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by xiaodong.jin on 2019/05/28.
 * description：
 */
public class DateUtils {

    public static String getTimeDurationStr(long duration) {
        try {
            StringBuffer sb = new StringBuffer();
            long l = duration / 1000;        //计算奔视频有多少秒
            long hour = l / 3600;                //计算有多少个小时
            long min = (l - hour * 3600) / 60;        //计算有多少分钟
            long sec = l % 60;        //计算有多少秒
            if (hour != 0) {
                if (hour < 10) {
                    sb.append("0" + hour + ":");
                } else {
                    sb.append(hour + ":");
                }
            }
            if (min < 10) {
                sb.append("0" + min + ":");
            } else {
                sb.append(min + ":");
            }
            if (sec < 10) {
                sb.append("0" + sec);
            } else {
                sb.append(sec);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getNetFileSizeDescription(long size) {
        StringBuffer bytes = new StringBuffer();
        DecimalFormat format = new DecimalFormat("###.0");
        if (size >= 1024 * 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0 * 1024.0));
            bytes.append(format.format(i)).append("GB");
        } else if (size >= 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0));
            bytes.append(format.format(i)).append("MB");
        } else if (size >= 1024) {
            double i = (size / (1024.0));
            bytes.append(format.format(i)).append("KB");
        } else if (size < 1024) {
            if (size <= 0) {
                bytes.append("请选择");
            } else {
                bytes.append((int) size).append("B");
            }
        }
        return bytes.toString();
    }

    public static String getImageTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Calendar imageTime = Calendar.getInstance();
        imageTime.setTimeInMillis(time);
        if (sameDay(calendar, imageTime)) {
            return "今天";
        } else if (sameWeek(calendar, imageTime)) {
            return "本周";
        } else if (sameMonth(calendar, imageTime)) {
            return "本月";
        } else {
            Date date = new Date(time);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
            return sdf.format(date);
        }
    }

    public static boolean sameDay(Calendar calendar1, Calendar calendar2) {
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR);
    }

    public static boolean sameWeek(Calendar calendar1, Calendar calendar2) {
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.WEEK_OF_YEAR) == calendar2.get(Calendar.WEEK_OF_YEAR);
    }

    public static boolean sameMonth(Calendar calendar1, Calendar calendar2) {
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH);
    }
}
