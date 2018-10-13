package com.test.shileiyu.jetpack.common.permission.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

public class PermissionDialog {

    public static Dialog getExplainPermissionDialog(Activity activity, DialogInterface.OnClickListener confirm, DialogInterface.OnClickListener cancel) {
        return get(activity, "温馨提醒", "您关闭了APP一些必要的权限，APP将无法使用部分功能，请开启", "去开启", "不开启"
                , confirm, cancel);
    }

    public static Dialog getOpenSettingDialog(Activity activity, DialogInterface.OnClickListener confirm) {
        return get(activity, "温馨提示", "跳转到Setting 手动开启权限", "去开启", "算了",
                confirm, null);
    }

    public static Dialog get(Activity activity
            , CharSequence title, CharSequence message
            , CharSequence confirmText
            , CharSequence cancelText
            , final DialogInterface.OnClickListener confirmListener
            , final DialogInterface.OnClickListener cancelListener) {
        return new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(confirmText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (confirmListener != null)
                            confirmListener.onClick(dialog, which);
                    }
                })
                .setNegativeButton(cancelText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (cancelListener != null)
                            cancelListener.onClick(dialog, which);
                    }
                })
                .create();
    }
}
