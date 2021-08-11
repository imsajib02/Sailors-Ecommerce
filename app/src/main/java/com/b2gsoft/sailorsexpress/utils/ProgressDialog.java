package com.b2gsoft.sailorsexpress.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import com.b2gsoft.sailorsexpress.R;

public class ProgressDialog {

    private static Dialog dialog;

    public static void show(Context context) {

        dismiss();

        dialog = new Dialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.progress_dialog);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }


    public static void dismiss() {

        if(dialog != null && dialog.isShowing()) {

            dialog.dismiss();
        }
    }
}
