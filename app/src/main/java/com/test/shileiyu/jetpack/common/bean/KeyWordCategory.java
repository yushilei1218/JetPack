package com.test.shileiyu.jetpack.common.bean;

import java.util.List;

/**
 * @author shilei.yu
 * @date 2018/8/9
 */

public class KeyWordCategory {
    //    displayName: "全部人群",
//    id: 6,
//    materialType: "metadata",
//    metadataValues: [],
//    name: "人群"
    public int id;
    public List<KeyWord> metadataValues;
    public String displayName;
    public String materialType;
    public String name;

    public boolean isSelected = false;

}
