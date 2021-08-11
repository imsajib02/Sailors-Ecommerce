package com.b2gsoft.sailorsexpress.utils;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.b2gsoft.sailorsexpress.R;

public class MySnack {

    private static Snackbar snack;

    public static void showGeneralSnackBar(View rootView, String message, int duration) {

        hideSnackBar();

        snack = Snackbar.make(rootView, message, 3000);

        View view = snack.getView();
        view.setBackgroundColor(rootView.getResources().getColor(R.color.colorAccent));

        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(15);
        tv.setTypeface(null, Typeface.BOLD);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        else
        {
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
        }

        snack.show();
    }


    private static void hideSnackBar() {

        if(snack != null && snack.isShownOrQueued()) {

            snack.dismiss();
        }
    }
}