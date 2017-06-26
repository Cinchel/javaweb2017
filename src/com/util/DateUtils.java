package com.util;

import org.json.JSONObject;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by onlymzzhang on 2017/6/26.
 */
public class DateUtils {
    public static java.sql.Date getNowDate() {
        Calendar now = Calendar.getInstance();
        return new Date(now.getTimeInMillis());
    }
    public static java.sql.Date getTomorrowDate() {
        Calendar now = Calendar.getInstance();
        return new Date(now.getTimeInMillis() + 1000*60*60*24);
    }
    public static java.util.Date getNowUtilDate() {
        Calendar now = Calendar.getInstance();
        return new java.util.Date(now.getTimeInMillis());
    }
}
