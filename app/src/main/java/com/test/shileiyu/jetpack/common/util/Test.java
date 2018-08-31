package com.test.shileiyu.jetpack.common.util;

import java.io.File;

/**
 * @author shilei.yu
 * @date 2018/8/31
 */

public class Test {

    public static void main(String[] a) {
        File file =new File("/Users/shilei.yu/Desktop/Loading/2x");
        if (file.exists()){
            System.out.println("存在"+file.toString());
            File[] files = file.listFiles();
            for (File f : files) {
                String name = f.getName();
                System.out.println(name);

                String newName=name.replace("@2x","");
                f.renameTo(new File(file,newName));
            }
        }
    }
}
