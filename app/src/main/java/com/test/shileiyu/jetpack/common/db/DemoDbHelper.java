package com.test.shileiyu.jetpack.common.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;

/**
 * @author lanche.ysl
 * @date 2018/10/15 下午4:16
 */
public class DemoDbHelper extends OrmLiteSqliteOpenHelper {
    public DemoDbHelper(Context context, String databaseName) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, ATable.class);
            TableUtils.createTableIfNotExists(connectionSource, BTable.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <D extends Dao<T, ?>, T> D getDao(Class<T> clazz) throws SQLException {
        return super.getDao(clazz);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
    }
}
