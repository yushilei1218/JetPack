package com.test.shileiyu.jetpack.common.permission;

import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 * @author lanche.ysl
 * @date 2018/10/12 下午7:24
 */
public class AskPermission {
    public static Source with(Activity activity) {
        return new Source.ActivitySource(activity);
    }

    public static Source with(Fragment fragment) {
        return new Source.FragmentSource(fragment);
    }
}
