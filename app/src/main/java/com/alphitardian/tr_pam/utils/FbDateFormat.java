package com.alphitardian.tr_pam.utils;

import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FbDateFormat {
    public static String getDate(long seconds, long nanoseconds){

        long milliSeconds = seconds * 1000 + nanoseconds / 1000000;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM, yyyy dd HH:ss a");
        Date newDate = new Date(milliSeconds);
        String result = simpleDateFormat.format(newDate).toString();

        return result;
    }
}
