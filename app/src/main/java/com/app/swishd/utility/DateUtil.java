package com.app.swishd.utility;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {
    public static final String SERVER_DATE = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String SERVER_DATE_ADVANCED_SEARCH = "dd/MM/yyyy";
    public static final String SIMPLE_DATE = "dd-MM-yyyy HH:mm";
    public static final String DISPLAY_DATE = "dd,MMM hh:mm a";
    public static final String SPACE = " ";

    public static String getFormatedDate(String dateString, String fromFormat, String toFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(fromFormat);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date date = sdf.parse(dateString);
            String formated = new SimpleDateFormat(toFormat).format(date);
            return formated;
        } catch (Exception e) {
            e.printStackTrace();
            return dateString;
        }
    }

    public static String getFormatedDateInUTC(String dateString, String fromFormat, String toFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(fromFormat);
        try {
            Date date = sdf.parse(dateString);
            SimpleDateFormat dateFormat = new SimpleDateFormat(toFormat);
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return dateString;
        }
    }

    public static String getFormatedDateWithoutUTC(String dateString, String fromFormat, String toFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(fromFormat);
        try {
            Date date = sdf.parse(dateString);
            String formated = new SimpleDateFormat(toFormat).format(date);
            return formated;
        } catch (Exception e) {
            e.printStackTrace();
            return dateString;
        }
    }

    public static String getSimpleDate(Calendar calendar) {
        return String.format(Locale.getDefault(), "%02d", calendar.get(Calendar.DAY_OF_MONTH)) + "-"
                + String.format(Locale.getDefault(), "%02d", calendar.get(Calendar.MONTH) + 1) + "-"
                + calendar.get(Calendar.YEAR) + " "
                + String.format(Locale.getDefault(), "%02d", calendar.get(Calendar.HOUR_OF_DAY)) + ":"
                + String.format(Locale.getDefault(), "%02d", calendar.get(Calendar.MINUTE));
    }

    public static void showDatePickerDialog(Context context,
                                            DatePickerDialog.OnDateSetListener listener,
                                            Calendar calendar, Calendar minCalender) {
        DatePickerDialog dialog = new DatePickerDialog(context, listener,
                                                       calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        if (minCalender != null)
            dialog.getDatePicker().setMinDate(minCalender.getTimeInMillis());
        dialog.show();
    }

    public static void showTimerPickerDialog(Context context, int hrs,
                                             int min, TimePickerDialog.OnTimeSetListener listener) {
        TimePickerDialog dialog = new TimePickerDialog(context, listener, hrs, min, true);
        dialog.show();
    }

    public static Calendar getCalender(String dateString, String fromFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(fromFormat);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = sdf.parse(dateString);
            calendar.setTimeInMillis(date.getTime());
            return calendar;
        } catch (Exception e) {
            e.printStackTrace();
            return calendar;
        }
    }

    public static long getMillis(String dateString, String fromFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(fromFormat);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date date = sdf.parse(dateString);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}

