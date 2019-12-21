package com.example.weatherapp.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateParser {

    public static String getData(Integer sunrise){
        Date date = new Date(sunrise * 1000L );
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
        format.setTimeZone(TimeZone.getTimeZone("GMT+06:00"));
        return format.format(date);
    }

    public static String foreCastDate(String s) throws ParseException {
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = dt.parse(s);
        SimpleDateFormat outDt = new SimpleDateFormat("dd.MMMM");
        String parseDate = outDt.format(date);
        return parseDate;
    }

    public static String getCityData(){
   return   new SimpleDateFormat("dd MMMM yyyy").format(new Date());
    }
}
