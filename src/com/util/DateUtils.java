package com.util;

import org.json.JSONObject;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by onlymzzhang on 2017/6/26.
 */
public class DateUtils {
    public static Date getNowDate() {
        Calendar now = Calendar.getInstance();
        return new Date(now.getTimeInMillis());
    }
}