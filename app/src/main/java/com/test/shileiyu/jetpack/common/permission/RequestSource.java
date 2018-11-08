package com.test.shileiyu.jetpack.common.permission;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

/**
 * 权限申请源
 * <p>
 * 用于包裹发起申请权限的来源，包裹Fragment or Activity
 *
 * @author lanche.ysl
 * @date 2018/10/12 下午7:35
 */
public abstract class RequestSource {
    /**
     * 从申请源中获取Activity
     *
     * @return activity or null
     */
    @Nullable
    public abstract Activity getActivity();

    /**
     * 询问当前申请源某个权限是否应该像用户解释申请原因
     *
     * @param permission 单个被申请的权限
     * @return true:这个权限需要向用户解释什么原因
     */
    public abstract boolean showRationale(String permission);

    /**
     * 创建权限请求
     *
     * @return 23以上MRequest ，以下 LRequest
     */
    public final IPermissionRequest createRequest() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return new MRequest(this);
        } else {
            return new LRequest(this);
        }
    }

    public final IPermissionRequest createLocalRequest() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return new MRequest(this, false);
        } else {
            return new LRequest(this);
        }
    }

    /**
     * 发起申请的源头的Activity是否处于有效状态
     */
    public boolean isActivityValid() {
        Activity activity = getActivity();
        return activity != null && (!activity.isFinishing());
    }

    public static class ActivityRequestSource extends RequestSource {
        private final Activity mActivity;

        ActivityRequestSource(@NonNull Activity activity) {
            mActivity = activity;
        }

        @Override
        public Activity getActivity() {
            return mActivity;
        }

        @Override
        public boolean showRationale(String permission) {
            return ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission);
        }
    }

    public static class V4FragmentRequestSource extends RequestSource {
        private final Fragment mFragment;
        private final Activity mActivity;

        V4FragmentRequestSource(@NonNull Fragment fragment) {
            mFragment = fragment;
            mActivity = fragment.getActivity();
        }

        @Override
        public Activity getActivity() {
            return mActivity;
        }

        @Override
        public boolean showRationale(String permission) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return mFragment.shouldShowRequestPermissionRationale(permission);
            }
            return false;
        }
    }

    public static class AppFragmentRequestSource extends RequestSource {
        private final android.app.Fragment mFragment;
        private final Activity mActivity;

        AppFragmentRequestSource(android.app.Fragment fragment) {
            mFragment = fragment;
            mActivity = fragment.getActivity();
        }

        @Nullable
        @Override
        public Activity getActivity() {
            return mActivity;
        }

        @Override
        public boolean showRationale(String permission) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return mFragment.shouldShowRequestPermissionRationale(permission);
            }
            return false;
        }
    }
}
