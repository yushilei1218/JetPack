package com.test.shileiyu.jetpack.common.permission;

import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

/**
 * @author lanche.ysl
 * @date 2018/10/12 下午8:22
 */
public class LRequest implements IPermissionRequest {
    private PermissionAction<List<String>> mAction;
    private String[] mPermissions;

    @Override
    public IPermissionRequest permission(@NonNull String... permission) {
        mPermissions = permission;
        return this;
    }

    @Override
    public IPermissionRequest onGranted(PermissionAction<List<String>> grantAction) {
        mAction = grantAction;
        return this;
    }

    @Override
    public IPermissionRequest showRationale(IRationale<List<String>> rationale) {
        return this;
    }

    @Override
    public IPermissionRequest onDenied(PermissionAction<List<String>> deniedAction) {
        return this;
    }

    @Override
    public void start() {
        if (mAction != null) {
            mAction.onCall(Arrays.asList(mPermissions));
        }
    }
}
