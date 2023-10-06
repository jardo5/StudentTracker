package com.example.studenttracker.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    private static final String DATE_FORMAT = "MM/dd/yyyy";

    public static String getCurrentFormattedDate() {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        return format.format(Calendar.getInstance().getTime());
    }

    public static Date convertStringToDate(String dateString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        return format.parse(dateString);
    }

    public static String convertDateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        return format.format(date);
    }
}
