package com.test.shileiyu.jetpack.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.db.ATable;
import com.test.shileiyu.jetpack.common.db.DemoDbHelper;
import com.test.shileiyu.jetpack.common.util.Util;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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
    }

    @OnClick({
            R.id.add_bean,
            R.id.add_list,
            R.id.clear_a
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
            default:
                break;
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
            if (!Util.isEmpty(aTables)) {
                StringBuilder sb = new StringBuilder();
                for (ATable a : aTables) {
                    sb.append(Util.toJson(a)).append("\n");
                }
                mTv.setText(sb.toString());
            } else {
                mTv.setText("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_ormlite;
    }
}
