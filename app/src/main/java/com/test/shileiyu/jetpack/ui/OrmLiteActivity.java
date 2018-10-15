package com.test.shileiyu.jetpack.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.db.ATable;
import com.test.shileiyu.jetpack.common.db.DemoDbHelper;
import com.test.shileiyu.jetpack.common.db.IDaoCall;
import com.test.shileiyu.jetpack.common.db.IDaoListCall;
import com.test.shileiyu.jetpack.common.util.Util;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.functions.Action1;

public class OrmLiteActivity extends BaseActivity {
    @BindView(R.id.data_base_tv)
    TextView mTv;

    private String mPreName = "Name+";
    private int index = 0;
    private Dao<ATable, ?> mDao;
    private DemoDbHelper mDbHelper;

    @Override
    protected void initView(Bundle savedInstanceState) {
        mDbHelper = new DemoDbHelper(this, "test.db");
        try {
            mDao = mDbHelper.getDao(ATable.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        queryAll();
    }

    @OnClick({
            R.id.add_bean,
            R.id.add_list,
            R.id.clear_a,
            R.id.save_or_update_list,
            R.id.query_builder,
            R.id.observable_list,
            R.id.create_or_update,
            R.id.count,
            R.id.observable
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_bean:
                addOneBean();
                break;
            case R.id.add_list:
                addListBean();
                break;
            case R.id.clear_a:
                clearATable();
                break;
            case R.id.query_builder:
                queryBuilder();
                break;
            case R.id.save_or_update_list:
                saveOrUpdateList();
                break;
            case R.id.observable:
                queryObservable();
                break;
            case R.id.observable_list:
                queryObservableList();
                break;
            case R.id.create_or_update:
                createOrUpdate();
                break;
            case R.id.count:
                count();
                break;
            default:
                break;
        }
    }

    private void count() {
        mDbHelper.rx2(ATable.class, new IDaoCall<Long, ATable>() {
            @Override
            public Long call(Dao<ATable, ?> dao) {
                try {
                    return dao.queryBuilder().where()
                            .eq("name", "Name+3")
                            .and().eq("state", 1).countOf();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }).subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Util.showToast("count查询报错" + e.getMessage());
            }

            @Override
            public void onNext(Long integer) {
                Util.showToast("查询到" + integer + "个");
            }
        });
    }

    private void createOrUpdate() {
        ATable aTable = new ATable();
        aTable.setId(1);
        aTable.setName("createOrUpdate 1");
        ATable btable = new ATable();
        btable.setId(72);
        btable.setName("createOrUpdate 72");
        try {
            mDao.createOrUpdate(aTable);
            mDao.createOrUpdate(btable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        queryAll();
    }

    private void queryObservableList() {
        mDbHelper.rx(ATable.class, new IDaoListCall<ATable>() {
            @Override
            public List<ATable> call(Dao<ATable, ?> dao) {
                try {
                    return dao.queryBuilder().where().eq("name", "Name+3").query();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }).subscribe(new Subscriber<List<ATable>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<ATable> aTables) {
                showInTextView(aTables);
            }
        });
    }

    private void queryObservable() {
        mDbHelper.rx(ATable.class, new IDaoCall<ATable, ATable>() {
            @Override
            public ATable call(Dao<ATable, ?> dao) {
                Log.d("ATable", "Call thread" + Thread.currentThread().getName());
                try {
                    return dao.queryBuilder().where().eq("name", "Name+3").queryForFirst();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
//                throw new RuntimeException("Test");
                return null;
            }
        }).subscribe(new Action1<ATable>() {
            @Override
            public void call(ATable aTable) {
                if (aTable != null) {
                    List<ATable> data = new ArrayList<>();
                    data.add(aTable);
                    showInTextView(data);
                } else {
                    showInTextView(null);
                }
                Log.d("ATable", "Subscribe thread" + Thread.currentThread().getName());
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Util.showToast(throwable.getMessage());
            }
        });
    }

    private void saveOrUpdateList() {
        try {
            List<ATable> name = mDao.queryBuilder().where().eq("name", "Name+3").query();
            ATable e = new ATable();
            e.setName("没错 我是一个新增的");
            name.add(e);
            if (!Util.isEmpty(name)) {
                for (ATable a : name) {
                    a.setName(a.getName() + " saveOrUpdate");
                    mDao.createOrUpdate(a);
                }
            }
            queryAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void queryBuilder() {
        try {
            List<ATable> name = mDao.queryBuilder().where().eq("name", "Name+3").query();
            showInTextView(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearATable() {
        ConnectionSource source = mDao.getConnectionSource();
        try {
            TableUtils.clearTable(source, ATable.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        queryAll();
    }

    private void addListBean() {
        List<ATable> data = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            ATable e = new ATable();
            index++;
            e.setName(mPreName + index);
            data.add(e);
        }
        try {
            mDao.create(data);
            queryAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addOneBean() {
        try {
            ATable aTable = new ATable();
            index++;
            aTable.setName(mPreName + index);
            mDao.create(aTable);
            queryAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void queryAll() {
        try {
            List<ATable> aTables = mDao.queryForAll();
            showInTextView(aTables);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showInTextView(List aTables) {
        if (!Util.isEmpty(aTables)) {
            StringBuilder sb = new StringBuilder();
            for (Object a : aTables) {
                sb.append(Util.toJson(a)).append("\n");
            }
            mTv.setText(sb.toString());
        } else {
            mTv.setText("");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_ormlite;
    }
}
