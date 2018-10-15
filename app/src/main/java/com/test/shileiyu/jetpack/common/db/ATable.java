package com.test.shileiyu.jetpack.common.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Random;

/**
 * @author lanche.ysl
 * @date 2018/10/15 下午4:19
 */
@DatabaseTable(tableName = "ATable")
public class ATable {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String name;
    @DatabaseField
    private int state = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public ATable() {
        state = new Random().nextInt(3);
    }
}
