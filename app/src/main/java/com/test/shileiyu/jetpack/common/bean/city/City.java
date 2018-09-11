package com.test.shileiyu.jetpack.common.bean.city;

import android.os.SystemClock;

import com.test.shileiyu.jetpack.common.bean.Constant;
import com.test.shileiyu.jetpack.common.util.ThreadPool;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shilei.yu
 * @date 2018/9/11
 */

public class City extends BaseComposite<District> {

    public City(int id, String name) {
        super(id, name);
    }

    @Override
    public void requestChildren(final SimpleCallBack<Boolean> callBack) {
        ThreadPool.submit(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(Constant.TIME);
                List<District> data = new ArrayList<>();

                for (int i = 0; i < 32; i++) {
                    data.add(new District(i, name + " " + i + "区"));
                }
                addChild(data);
                resetChildren();

                callBack.onCall(true);
            }
        });
    }
}
