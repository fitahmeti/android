package com.app.swishd.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.StringRes;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.app.swishd.R;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Utility {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static void LOG(String tag, String value) {
        Log.i(tag, "==>" + value);
    }

    public static void LOG(String tag, int value) {
        Log.i(tag, "==>" + String.valueOf(value));
    }

    public static void LOG(String tag, float value) {
        Log.i(tag, "==>" + String.valueOf(value));
    }

    public static void showToast(Context context, @StringRes int message) {
        if (context == null)
            return;
        Toast.makeText(context, context.getString(message),
                Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String message) {
        if (context == null || message == null || message.isEmpty())
            return;
        Toast.makeText(context, message,
                Toast.LENGTH_SHORT).show();
    }

    public static String nullCheck(String value) {
        return (value == null) ? "" : value;
    }

    public static Address getAddress(Context context, double lat, double lng) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        if (!geocoder.isPresent()) {
            showToast(context, R.string.e_service_not_available);
            return null;
        }

        Address obj = null;
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses.size() > 0)
                obj = addresses.get(0);
            return obj;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void composeEmail(Context context, String[] addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
//        intent.putExtra(Intent.EXTRA_TEXT, "");
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "You don't have mail client installed", Toast.LENGTH_SHORT).show();
        }
    }

    public static void openWebPage(Context context, String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "Browser app not found to perform this action", Toast.LENGTH_SHORT).show();
        }
    }

    public static String getAgoStringFromTime(long dateMillis) {
        HashMap<String, Integer> timeScale = new HashMap<>();
        timeScale.put("sec", 1);
        timeScale.put("min", 60);
        timeScale.put("hour", 3600);
        timeScale.put("day", 86400);
        timeScale.put("week", 605800);
        timeScale.put("month", 2629743);
        timeScale.put("year", 31556926);
        String scale = "";
        long timeAgo = TimeUnit.MILLISECONDS.toSeconds(Calendar.getInstance().getTimeInMillis()) - TimeUnit.MILLISECONDS.toSeconds(dateMillis);
        if (timeAgo < 60) {
            scale = "sec";
            return "Just now";
        } else if (timeAgo < 3600) {
            scale = "min";
        } else if (timeAgo < 86400) {
            scale = "hour";
        } else if (timeAgo < 172800) {
            return "Yesterday";
        } else if (timeAgo < 605800) {
            scale = "day";
        } else if (timeAgo < 2629743) {
            scale = "week";
        } else if (timeAgo < 31556926) {
            scale = "month";
        } else {
            scale = "year";
        }
        long ago = timeAgo / timeScale.get(scale);
        String s = "";
        if (ago > 1) {
            s = "s";
        }

        return ago + " " + scale + "" + s + " " + "ago";
    }

    public static SpannableStringBuilder getBoldString(String text, String name) {
        SpannableStringBuilder str = new SpannableStringBuilder(text);
        int textPosition = text.indexOf(name);
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
                textPosition, textPosition + name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return str;
    }
}
