package com.test.shileiyu.jetpack.common.permission.action;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import com.test.shileiyu.jetpack.common.permission.PermissionAction;
import com.test.shileiyu.jetpack.common.permission.dialog.PermissionDialog;

import java.util.List;

public class GuideUser2SettingAction implements PermissionAction<List<String>> {
    private final Activity activity;

    public GuideUser2SettingAction(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onCall(List<String> data) {
        if (activity != null && !activity.isFinishing()) {
            PermissionDialog.getOpenSettingDialog(activity, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
                    activity.startActivity(intent);
                }
            }).show();
        }
    }
}
