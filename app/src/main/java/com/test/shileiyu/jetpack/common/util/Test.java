package com.test.shileiyu.jetpack.common.util;

import com.test.shileiyu.jetpack.common.bean.Bean;

import java.io.File;

/**
 * @author shilei.yu
 * @date 2018/8/31
 */

public class Test implements Cloneable {
    private boolean b;
    private String name;
    private Bean mBean;

    public Test(boolean b, String name, Bean bean) {
        this.b = b;
        this.name = name;
        mBean = bean;
    }

    public static void main(String[] a) {
//        File file = new File("/Users/shilei.yu/Desktop/Loading/2x");
//        if (file.exists()) {
//            System.out.println("存在" + file.toString());
//            File[] files = file.listFiles();
//            for (File f : files) {
//                String name = f.getName();
//                System.out.println(name);
//
//                String newName = name.replace("@2x", "");
//                f.renameTo(new File(file, newName));
//            }
//        }
        Test test1 = new Test(true, "name1", new Bean("bean1"));
        System.out.println(test1.toString());
        Test clone = (Test) test1.clone();
        System.out.println(clone.toString());
        System.out.println(clone.equals(test1));
    }

    @Override
    public String toString() {
        return this.hashCode()+" b=" + b + " name=" + name + " mBean=" + mBean.toString();
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (Exception i) {
            return null;
        }
    }
}
