package com.test.shileiyu.jetpack.common.permission;

import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 * 权限申请的入口类
 * <p>
 * eg:
 * AskPermission.with((Activity) context)
 * .createRequest()
 * .permission(
 * Manifest.permission.ACCESS_FINE_LOCATION,
 * Manifest.permission.ACCESS_COARSE_LOCATION)
 * .onDenied(new GuideUser2SettingAction())
 * .onGranted(new IPermissionAction() {
 * public void onCall(RequestSource requestSource, List<String> list) {
 * //成功获取到权限 do something
 * }
 * })
 * .showRationale(new ExplainPermissionRationale())
 * .start();
 *
 * @author lanche.ysl
 * @date 2018/10/15 上午10:30
 */
public final class AskPermission {
    private AskPermission() {
    }

    public static RequestSource with(Activity activity) {
        return new RequestSource.ActivityRequestSource(activity);
    }

    public static RequestSource with(Fragment fragment) {
        return new RequestSource.V4FragmentRequestSource(fragment);
    }

    public static RequestSource with(android.app.Fragment fragment) {
        return new RequestSource.AppFragmentRequestSource(fragment);
    }
}
