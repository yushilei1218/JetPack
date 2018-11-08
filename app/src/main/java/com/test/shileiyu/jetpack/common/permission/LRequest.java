package com.test.shileiyu.jetpack.common.permission;

import android.support.annotation.NonNull;

import java.util.Arrays;

/**
 * @author lanche.ysl
 * @date 2018/10/12 下午8:22
 */
public class LRequest implements IPermissionRequest {
    private final RequestSource mRequestSource;
    private IPermissionAction mAction;
    private String[] mPermissions;


    public LRequest(RequestSource requestSource) {
        this.mRequestSource = requestSource;
    }

    @Override
    public IPermissionRequest permission(@NonNull String... permission) {
        mPermissions = permission;
        return this;
    }

    @Override
    public IPermissionRequest onGranted(IPermissionAction grantAction) {
        mAction = grantAction;
        return this;
    }

    @Override
    public IPermissionRequest showRationale(IRationale rationale) {
        return this;
    }

    @Override
    public IPermissionRequest onDenied(IPermissionAction deniedAction) {
        return this;
    }

    @Override
    public void start() {
        if (mAction != null) {
            mAction.onCall(mRequestSource, Arrays.asList(mPermissions));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }
}
