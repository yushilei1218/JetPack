package com.test.shileiyu.jetpack.common.permission;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.test.shileiyu.jetpack.ui.PermissionGetActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lanche.ysl
 * @date 2018/10/12 下午7:34
 */
public class MRequest implements IPermissionRequest, RequestExecutor {
    private PermissionChecker mChecker = new StandChecker();
    private Source mSource;

    private List<String> mRequestPermissions;
    private Action<List<String>> mGrantAction;
    private Action<List<String>> mDeniedAction;
    private IRationale<List<String>> mRationale;

    private PermissionCallBack mCallBack = new PermissionCallBack() {
        @Override
        public void onPermissionFinish(@NonNull String[] permissions, @NonNull int[] grantResults) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Context context = mSource.getContext();
                    if (context != null) {
                        List<String> deniedPermission = getDeniedPermission(context, mChecker, mRequestPermissions);
                        if (deniedPermission == null || deniedPermission.size() == 0) {
                            callBackSuccess();
                        } else {
                            callBackFailed(deniedPermission);
                        }
                    }
                }
            }, 100);

        }
    };


    public MRequest(Source source) {
        mSource = source;
    }

    @Override
    public IPermissionRequest permission(@NonNull String... permission) {
        if (mRequestPermissions == null) {
            mRequestPermissions = new ArrayList<>();
        }
        mRequestPermissions.clear();
        mRequestPermissions.addAll(Arrays.asList(permission));
        return this;
    }

    @Override
    public IPermissionRequest onGranted(Action<List<String>> grantAction) {
        mGrantAction = grantAction;
        return this;
    }

    @Override
    public IPermissionRequest showRationale(IRationale<List<String>> rationale) {
        mRationale = rationale;
        return this;
    }

    @Override
    public IPermissionRequest onDenied(Action<List<String>> deniedAction) {
        mDeniedAction = deniedAction;
        return this;
    }

    @Override
    public void start() {
        Context context = mSource.getContext();
        if (context == null) {
            return;
        }
        boolean hasPermission = mChecker.hasPermission(context, mRequestPermissions);
        if (hasPermission) {
            callBackSuccess();
        } else {
            List<String> mNeedRationalePermission = new ArrayList<>();
            for (String p : mRequestPermissions) {
                boolean showRationale = mSource.showRationale(p);
                if (showRationale) {
                    mNeedRationalePermission.add(p);
                }
            }
            if (mNeedRationalePermission.size() > 0) {
                if (mRationale != null) {
                    mRationale.showRationale(mNeedRationalePermission, this);
                }
            } else {
                execute();
            }

        }
    }

    public void execute() {
        String[] permission = new String[mRequestPermissions.size()];
        mRequestPermissions.toArray(permission);
        Context context = mSource.getContext();
        if (context == null)
            return;
        PermissionGetActivity.requestPermission(context, permission, mCallBack);
    }

    @Override
    public void cancel() {

    }

    private void callBackSuccess() {
        if (mGrantAction != null) {
            mGrantAction.onCall(mRequestPermissions);
        }
    }

    private void callBackFailed(List<String> denied) {
        if (mDeniedAction != null) {
            mDeniedAction.onCall(denied);
        }
    }

    private static List<String> getDeniedPermission(Context context, PermissionChecker checker, List<String> requestPermissions) {
        if (checker != null) {
            if (requestPermissions != null && requestPermissions.size() > 0) {
                List<String> denied = new ArrayList<>();
                for (String p : requestPermissions) {
                    boolean hasPermission = checker.hasPermission(context, p);
                    if (!hasPermission) {
                        denied.add(p);
                    }
                }
                return denied;
            }
        }
        return null;
    }
}
