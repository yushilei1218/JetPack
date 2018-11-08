package com.test.shileiyu.jetpack.common.permission;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import java.util.List;

/**
 * 提供一个通用的解释权限 Rationale
 *
 * @author lanche.ysl
 * @date 2018/10/15 上午11:18
 */
public class ExplainPermissionRationale implements IRationale {


    public ExplainPermissionRationale() {
    }

    public ExplainPermissionRationale(boolean isForceRequest) {
        this.isForceRequest = isForceRequest;
    }

    /**
     * 是否强制用户一定要申请权限 -直接关系到"权限解释对话框"是否有 取消 BTN
     */
    private boolean isForceRequest = false;

    private static final String COMMON_EXPLAIN = "需要打开相关权限才能获取基础服务";

    @SuppressWarnings("ConstantConditions")
    @Override
    public void showRationale(List<String> data, final IRequestExecutor executor, RequestSource source) {
        if (source != null && source.isActivityValid()) {
            final Activity activity = source.getActivity();
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setCancelable(false)
                    .setMessage(getExplainMessage(data))
                    .setPositiveButton("去打开", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            executor.execute();
                        }
                    });
            //非强制性解释，可以显示取消btn
            if (!isForceRequest) {
                builder.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        executor.cancel();
                    }
                });
            }
            builder.create().show();
        }
    }

    /**
     * 子类可以重写这个方法，提供解释权限的具体文案
     *
     * @param explains 需要解释的权限
     * @return 默认返回通用的文案
     */
    protected CharSequence getExplainMessage(List<String> explains) {
        return Util.getPermissionMsg(explains);
    }
}
