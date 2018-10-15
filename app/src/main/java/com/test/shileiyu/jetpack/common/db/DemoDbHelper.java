package com.test.shileiyu.jetpack.common.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Scheduler;
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

    public <T> Observable<List<T>> rx(final Class<T> clazz, final IDaoListCall<T> call) {
        return Observable
                .create(new Observable.OnSubscribe<List<T>>() {
                    @Override
                    public void call(Subscriber<? super List<T>> subscriber) {
                        try {
                            Dao<T, ?> dao = getDao(clazz);
                            List<T> data = call.call(dao);
                            subscriber.onNext(data);
                            subscriber.onCompleted();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public <T, C> Observable<T> rx2(final Class<C> table, final IDaoCall<T, C> call) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                Dao<C, ?> dao = null;
                try {
                    dao = getDao(table);
                    T call1 = call.call(dao);
                    subscriber.onNext(call1);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public <T> Observable<T> rx(final Class<T> clazz, final IDaoCall<T, T> call) {
        return Observable
                .create(new Observable.OnSubscribe<T>() {
                    @Override
                    public void call(Subscriber<? super T> subscriber) {
                        try {
                            Dao<T, ?> dao = getDao(clazz);
                            T data = call.call(dao);
                            subscriber.onNext(data);
                            subscriber.onCompleted();
                        } catch (Exception e) {
                            e.printStackTrace();
                            subscriber.onError(e);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
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
