package com.test.shileiyu.jetpack.common.permission;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

/**
 * @author lanche.ysl
 * @date 2018/10/12 下午7:35
 */
public abstract class RequestSource {

    public abstract Activity getActivity();

    public abstract boolean showRationale(String permission);

    public final IPermissionRequest createRequest() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return new MRequest(this);
        } else {
            return new LRequest();
        }
    }

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

    public static class FragmentRequestSource extends RequestSource {
        private final Fragment mFragment;
        private final Activity mActivity;

        FragmentRequestSource(@NonNull Fragment fragment) {
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
}
