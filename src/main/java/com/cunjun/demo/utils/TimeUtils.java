package com.cunjun.demo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Cunjun Wang (zhixin) on 2022/11/4
 */
public class TimeUtils {

    /**
     * 构造预计到达时间
     */
    public static String computeEstimatedArrivalTime(String departTime, Long duration) {
        SimpleDateFormat minuteFormat = new SimpleDateFormat("HH:mm");
        try {
            Date date = minuteFormat.parse(departTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MINUTE, duration.intValue());
            date = calendar.getTime();
            return minuteFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
