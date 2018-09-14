package com.test.shileiyu.jetpack.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.adapter.BeanAdapter;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.bean.Bean;

import butterknife.BindView;

/**
 * @author shilei.yu
 */
public class ScrollConflictActivity extends BaseActivity {
    @BindView(R.id.lv_scroll)
    ListView mListView;

    @Override
    protected void initView(Bundle savedInstanceState) {
        BeanAdapter adapter = new BeanAdapter();
        adapter.data = Bean.getList();
        mListView.setAdapter(adapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_scroll_confilct;
    }
}
