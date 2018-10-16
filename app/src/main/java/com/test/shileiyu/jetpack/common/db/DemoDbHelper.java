package com.test.shileiyu.jetpack.common.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author lanche.ysl
 * @date 2018/10/15 下午4:16
 */
public class DemoDbHelper extends OrmLiteSqliteOpenHelper {
    private static final List<Class> TABLES = new ArrayList<>();

    static {
        TABLES.add(ATable.class);
        TABLES.add(BTable.class);
    }

    public DemoDbHelper(Context context, String databaseName) {
        super(context, databaseName, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        Log.d("DemoDbHelper", "onCreate");
        for (Class table : TABLES) {
            try {
                TableUtils.createTableIfNotExists(connectionSource, table);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public <D extends Dao<T, ?>, T> D getDao(Class<T> clazz) throws SQLException {
        return super.getDao(clazz);
    }

    public <R, T> Observable<R> rx(final Class<T> table, final ITransfer<R, T> transfer) {
        return Observable.create(new Observable.OnSubscribe<R>() {
            @Override
            public void call(Subscriber<? super R> subscriber) {
                try {
                    Dao<T, ?> dao = getDao(table);
                    R back = transfer.call(dao);
                    subscriber.onNext(back);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {

        return super.getWritableDatabase();
    }

    @Override

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        Log.d("DemoDbHelper", "onUpgrade " + i + " " + i1);
        for (Class t : TABLES) {
            try {
                TableUtils.dropTable(connectionSource, t, true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        onCreate(sqLiteDatabase, connectionSource);
    }
}
