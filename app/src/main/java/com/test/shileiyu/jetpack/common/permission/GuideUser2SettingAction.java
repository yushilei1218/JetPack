package com.test.shileiyu.jetpack.common.permission;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import java.util.List;

/**
 * @author lanche.ysl
 * @date 2018/10/15 上午11:19
 */
public class GuideUser2SettingAction implements IPermissionAction {
    public GuideUser2SettingAction() {
    }

    public GuideUser2SettingAction(boolean isForceRequest) {
        this.isForceRequest = isForceRequest;
    }

    /**
     * 是否强制性要求打开设置页面- 直接影响"权限跳转设置页对话框" 取消BTN是否展示
     */
    private boolean isForceRequest = false;

    private static final String COMMON_MESSAGE = "需要打开相关权限才能获取基础服务";

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onCall(final RequestSource source, List<String> data) {
        if (source != null && source.isActivityValid()) {
            final Activity activity = source.getActivity();
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setCancelable(false)
                    .setMessage(get2SettingMessage(data))
                    .setPositiveButton("去打开", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                            SettingPageHelper.toPermissionPage(source.getActivity());
                        }
                    });
            //非强制打开设置，可以显示 取消btn
            if (!isForceRequest) {
                builder.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            }
            builder.create().show();

        }
    }

    /**
     * 默认通过通用的用户告知文案，提示用户跳转到setting手动开启权限
     * <p>
     * 子类可以重写这个方法提供自己的文案
     *
     * @param denied 被拒绝的权限
     * @return 默认提示手动开启权限文案
     */
    protected CharSequence get2SettingMessage(List<String> denied) {
        return Util.getPermissionMsg(denied);
    }
}
