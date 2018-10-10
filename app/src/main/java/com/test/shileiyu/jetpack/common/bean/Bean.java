package com.test.shileiyu.jetpack.common.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shilei.yu
 * @date 2018/8/20
 */

public class Bean implements Cloneable ,Serializable {
    public String name;

    public List<String> urls = new ArrayList<>();

    public Bean(String name) {
        this.name = name;
    }

    public static List<Bean> getList() {
        ArrayList<Bean> beans = new ArrayList<>(50);
        for (int i = 0; i < 50; i++) {
            beans.add(new Bean("item+ " + i));
        }
        return beans;
    }

    public static List<Bean> getList(String namePrex) {
        ArrayList<Bean> beans = new ArrayList<>(50);
        for (int i = 0; i < 50; i++) {
            beans.add(new Bean(namePrex + " " + i));
        }
        return beans;
    }

    public static List<Bean> getList(String namePrex,int size) {
        ArrayList<Bean> beans = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            beans.add(new Bean(namePrex + " " + i));
        }
        return beans;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
