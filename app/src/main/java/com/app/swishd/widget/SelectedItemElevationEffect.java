package com.app.swishd.widget;


import android.os.Build;
import android.support.v7.widget.CardView;
import android.view.MotionEvent;
import android.view.View;

public class SelectedItemElevationEffect implements CardView.OnTouchListener {

    @Override
    public boolean onTouch(View v, MotionEvent event) {

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                v.setElevation(30f);
//            } else if (event.getAction() == MotionEvent.ACTION_UP ||
//                    event.getAction() == MotionEvent.ACTION_CANCEL ||
//                    event.getAction()==MotionEvent.ACTION_OUTSIDE) {
//                v.setElevation(0.0f);
//            }
//        }
        return false;
    }
}
