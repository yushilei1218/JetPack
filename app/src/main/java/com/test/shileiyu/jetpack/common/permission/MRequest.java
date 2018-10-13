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
    private final PermissionChecker mChecker = new StandChecker();
    private final RequestSource mRequestSource;

    private List<String> mRequestPermissions;
    private PermissionAction<List<String>> mGrantAction;
    private PermissionAction<List<String>> mDeniedAction;
    private IRationale<List<String>> mRationale;

    private final PermissionCallBack mCallBack = new PermissionCallBack() {
        @Override
        public void onPermissionFinish(@NonNull String[] permissions, @NonNull int[] grantResults) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    dispatchCallBack();
                }
            }, 100);

        }
    };


    public MRequest(RequestSource requestSource) {
        mRequestSource = requestSource;
    }

    @Override
    public IPermissionRequest permission(@NonNull String... permission) {
        if (permission == null || permission.length == 0) {
            throw new RuntimeException("申请的权限数组为空");
        }
        if (mRequestPermissions == null) {
            mRequestPermissions = new ArrayList<>();
        }
        mRequestPermissions.clear();
        mRequestPermissions.addAll(Arrays.asList(permission));
        return this;
    }

    @Override
    public IPermissionRequest onGranted(PermissionAction<List<String>> grantAction) {
        mGrantAction = grantAction;
        return this;
    }

    @Override
    public IPermissionRequest showRationale(IRationale<List<String>> rationale) {
        mRationale = rationale;
        return this;
    }

    @Override
    public IPermissionRequest onDenied(PermissionAction<List<String>> deniedAction) {
        mDeniedAction = deniedAction;
        return this;
    }

    @Override
    public void start() {
        Context context = mRequestSource.getActivity();
        if (context == null) {
            return;
        }
        boolean hasPermission = mChecker.hasPermission(context, mRequestPermissions);
        if (hasPermission) {
            callBackSuccess();
        } else {
            boolean isCallRationale = doRationale();
            if (!isCallRationale) {
                execute();
            }
        }
    }

    @Override
    public void execute() {
        if (mRequestSource.isActivityValid()) {
            String[] permission = new String[mRequestPermissions.size()];
            mRequestPermissions.toArray(permission);
            PermissionGetActivity.requestPermission(mRequestSource.getActivity(), permission, mCallBack);
        }
    }

    private boolean doRationale() {
        List<String> mNeedRationalePermission = new ArrayList<>();
        for (String p : mRequestPermissions) {
            boolean showRationale = mRequestSource.showRationale(p);
            if (showRationale) {
                mNeedRationalePermission.add(p);
            }
        }
        if (mNeedRationalePermission.size() > 0) {
            if (mRationale != null) {
                mRationale.showRationale(mNeedRationalePermission, this, mRequestSource);
            }
        }
        return mNeedRationalePermission.size() > 0;
    }

    private void dispatchCallBack() {
        if (mRequestSource.isActivityValid()) {
            boolean isCallRationale = doRationale();
            if (!isCallRationale) {
                List<String> deniedPermission = getDeniedPermission(mRequestSource.getActivity(), mChecker, mRequestPermissions);
                if (deniedPermission == null || deniedPermission.size() == 0) {
                    callBackSuccess();
                } else {
                    callBackFailed(deniedPermission);
                }
            }
        }
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
