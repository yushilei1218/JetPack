package com.test.shileiyu.jetpack.common.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author lanche.ysl
 * @date 2018/10/15 下午4:38
 */
@DatabaseTable
public class BTable {
    @DatabaseField(id = true)
    public int pid;
    @DatabaseField
    public String name;
}
