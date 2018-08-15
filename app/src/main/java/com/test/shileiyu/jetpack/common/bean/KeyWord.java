package com.test.shileiyu.jetpack.common.bean;

/**
 * @author shilei.yu
 * @date 2018/8/9
 */

public class KeyWord {
//    displayName: "免费",
//    id: 2722,
//    name: "0"

    public KeyWordCategory parent;
    public String displayName;
    public int id;
    public String name;

    public boolean isSelect = false;

    public KeyWord() {
    }

    public KeyWord(KeyWordCategory data) {
        displayName = data.displayName;
        id = data.id;
    }
}
