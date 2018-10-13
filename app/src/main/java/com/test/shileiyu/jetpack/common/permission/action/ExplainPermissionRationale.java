package com.test.shileiyu.jetpack.common.permission.action;

import android.app.Activity;
import android.content.DialogInterface;

import com.test.shileiyu.jetpack.common.permission.IRationale;
import com.test.shileiyu.jetpack.common.permission.PermissionAction;
import com.test.shileiyu.jetpack.common.permission.RequestExecutor;
import com.test.shileiyu.jetpack.common.permission.RequestSource;
import com.test.shileiyu.jetpack.common.permission.dialog.PermissionDialog;

import java.util.List;

public class ExplainPermissionRationale implements IRationale<List<String>> {

    public ExplainPermissionRationale() {
    }

    @Override
    public void showRationale(List<String> data, final RequestExecutor executor, RequestSource source) {
        if (source.isActivityValid()) {
            PermissionDialog.getExplainPermissionDialog(source.getActivity(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    executor.execute();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    executor.cancel();
                }
            }).show();
        }
    }
}
