package com.test.shileiyu.jetpack.common.permission;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.yanzhenjie.permission.source.FragmentSource;

/**
 * @author lanche.ysl
 * @date 2018/10/12 下午7:35
 */
public abstract class Source {

    public abstract Context getContext();

    public abstract boolean showRationale(String permission);

    public IPermissionRequest createRequest() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return new MRequest(this);
        } else {
            return new LRequest();
        }
    }

    public static class ActivitySource extends Source {
        final Activity mActivity;

        public ActivitySource(Activity activity) {
            mActivity = activity;
        }

        @Override
        public Context getContext() {
            return mActivity;
        }

        @Override
        public boolean showRationale(String permission) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return mActivity.shouldShowRequestPermissionRationale(permission);
            }
            return false;
        }
    }

    public static class FragmentSource extends Source {
        final Fragment mFragment;

        FragmentSource(Fragment fragment) {
            mFragment = fragment;
        }

        @Override
        public Context getContext() {
            return mFragment.getActivity();
        }

        @Override
        public boolean showRationale(String permission) {
            return mFragment.shouldShowRequestPermissionRationale(permission);
        }
    }
}
