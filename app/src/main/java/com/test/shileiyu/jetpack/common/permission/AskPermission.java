package com.test.shileiyu.jetpack.common.permission;

import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 * @author lanche.ysl
 * @date 2018/10/12 下午7:24
 */
public class AskPermission {
    public static RequestSource with(Activity activity) {
        return new RequestSource.ActivityRequestSource(activity);
    }

    public static RequestSource with(Fragment fragment) {
        return new RequestSource.FragmentRequestSource(fragment);
    }
}
