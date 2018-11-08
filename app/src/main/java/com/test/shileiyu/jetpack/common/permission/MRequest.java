package com.test.shileiyu.jetpack.common.permission;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lanche.ysl
 * @date 2018/10/12 下午7:34
 */
public class MRequest implements IPermissionRequest, IRequestExecutor {

    private final IPermissionChecker mChecker = new StandChecker();
    private final RequestSource mRequestSource;

    private List<String> mRequestPermissions;

    private IPermissionAction mGrantAction;

    private IPermissionAction mDeniedAction;

    private IRationale mRationale;

    private boolean isNeedRequestDelegateActivity = true;

    private static final int CODE_PERMISSION_REQUEST = 0x536;

    private final IPermissionCallBack mCallBack = new IPermissionCallBack() {
        @Override
        public void onPermissionFinish(@NonNull String[] permissions, @NonNull int[] grantResults) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    dispatchRequestPermissionResult();
                }
            }, 100);

        }
    };


    public MRequest(RequestSource requestSource) {
        mRequestSource = requestSource;
    }

    public MRequest(RequestSource requestSource, boolean needDelegate) {
        mRequestSource = requestSource;
        isNeedRequestDelegateActivity = needDelegate;
    }

    @SuppressWarnings("ConstantConditions")
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
    public IPermissionRequest onGranted(IPermissionAction grantAction) {
        mGrantAction = grantAction;
        return this;
    }

    @Override
    public IPermissionRequest showRationale(IRationale rationale) {
        mRationale = rationale;
        return this;
    }

    @Override
    public IPermissionRequest onDenied(IPermissionAction deniedAction) {
        mDeniedAction = deniedAction;
        return this;
    }

    @Override
    public void start() {
        Context context = mRequestSource.getActivity();
        if (context == null) {
            return;
        }
        //1.首先检查 申请的权限是否已经拥有
        boolean hasPermission = mChecker.hasPermission(context, mRequestPermissions);
        if (hasPermission) {
            //2.如果申请的权限全部已获得，则回调成功
            callBackSuccess();
        } else {
            //3.一个或者多个权限未获得，优先检查是否有一个或多个权限需要给用户展示解释对话框
            boolean isCallRationale = doRationale();
            if (!isCallRationale) {
                //4.申请的权限中至少有一个未获得，且没有权限需要解释
                execute();
            }
        }
    }

    @Override
    public void execute() {
        if (mRequestSource.isActivityValid()) {
            String[] permission = new String[mRequestPermissions.size()];
            mRequestPermissions.toArray(permission);

            Activity activity = mRequestSource.getActivity();

            if (isNeedRequestDelegateActivity) {
                //如果需要使用代理Activity
                PermissionDelegateActivity.requestPermission(activity, permission, mCallBack);
            } else {
                //否则使用权限发起者 申请权限
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (activity != null) {
                        activity.requestPermissions(permission, CODE_PERMISSION_REQUEST);
                    }
                }
            }
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

    /**
     * 该方法用于分发权限请求回调后的结果
     */
    private void dispatchRequestPermissionResult() {
        if (mRequestSource.isActivityValid()) {
            List<String> deniedPermission = getDeniedPermission(mRequestSource.getActivity(), mChecker, mRequestPermissions);
            if (deniedPermission == null || deniedPermission.size() == 0) {
                //1.如果所有权限均已获得，success
                callBackSuccess();
            } else {
                //2.如果仍然有权限没能获取，优先检查是否包含需要解释的权限
                boolean isCanRationale = doRationale();
                if (isCanRationale) {
                    return;
                }
                //3.存在未获取的权限，并且不存在需要解释的权限则回调failed
                callBackFailed(deniedPermission);
            }
        }
    }

    @Override
    public void cancel() {

    }

    private void callBackSuccess() {
        //当利用系统API检测已经获取权限时（实际并未授权），真正使用权限时报错情况下，则会触发调用callBackFailed()
        try {
            if (mGrantAction != null) {
                mGrantAction.onCall(mRequestSource, mRequestPermissions);
            }
        } catch (Exception e) {
            e.printStackTrace();

            callBackFailed(mRequestPermissions);
        }
    }

    private void callBackFailed(List<String> denied) {
        if (mDeniedAction != null) {
            mDeniedAction.onCall(mRequestSource, denied);
        }
    }

    private static List<String> getDeniedPermission(Context context, IPermissionChecker checker, List<String> requestPermissions) {
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CODE_PERMISSION_REQUEST) {
            dispatchRequestPermissionResult();
        }
    }
}
