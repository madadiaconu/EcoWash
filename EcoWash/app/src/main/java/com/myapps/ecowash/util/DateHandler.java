package com.myapps.ecowash.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHandler {

    private static String format = "dd-MM-yyyy";

    public static Date currentDate(){
        return new Date();
    }

    public static String currentDateToString(){
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String dateToString(Date date){
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static Date getNextDate(Date myDate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(myDate);
        cal.add(Calendar.DATE, 1); //minus number would decrement the days
        return cal.getTime();
    }

}
