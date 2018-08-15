package com.test.shileiyu.jetpack.ui.home;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.bean.SubTab;
import com.test.shileiyu.jetpack.common.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabActivity extends AppCompatActivity {

    @BindView(R.id.tab_grid)
    GridView tabGrid;

    private TabViewModel mViewModel;

    private Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        ButterKnife.bind(this);

        mAdapter = new Adapter();

        tabGrid.setAdapter(mAdapter);

        tabGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SubTab subTab = mAdapter.data.get(position);
                CategoryActivity.invoke(TabActivity.this, subTab);
            }
        });

        mViewModel = ViewModelProviders.of(this).get(TabViewModel.class);

        mViewModel.mModel.mLiveData.observe(this, new Observer<List<SubTab>>() {
            @Override
            public void onChanged(@Nullable List<SubTab> subTabs) {
                mAdapter.data = subTabs;
                mAdapter.notifyDataSetChanged();
            }
        });
        mViewModel.getTab();
    }

    public static final class Adapter extends BaseAdapter {
        List<SubTab> data;

        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid, parent, false);

            }
            TextView tv = convertView.findViewById(R.id.item_tv);
            SubTab subTab = data.get(position);
            tv.setText(Util.toJson(subTab));

            return convertView;
        }
    }
}
