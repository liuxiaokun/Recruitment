package com.hou.recruitment.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface.OnCancelListener;
import android.view.Gravity;

public class ProgressDialogHelper {

    private static ProgressDialog mProgressDialog;

    public static void show(Activity activity, String title,
                            boolean isCancelable, OnCancelListener listener) {

        mProgressDialog = new ProgressDialog(activity);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);
        mProgressDialog.setCancelable(isCancelable);

        if (AppUtil.isNotEmpty(title)) {
            mProgressDialog.setMessage(title);
        }

        if (null != listener) {
            mProgressDialog.setOnCancelListener(listener);
        }

        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        mProgressDialog.show();
    }

    public static void dismiss() {

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}