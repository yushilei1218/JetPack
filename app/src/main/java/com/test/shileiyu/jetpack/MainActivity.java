package com.test.shileiyu.jetpack;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.test.shileiyu.jetpack.common.bean.Bean;
import com.test.shileiyu.jetpack.ui.ActivityLifeCycleActivity;
import com.test.shileiyu.jetpack.ui.AppBarLayoutActivity;
import com.test.shileiyu.jetpack.ui.AreaViewActivity;
import com.test.shileiyu.jetpack.ui.BehaviorActivity;
import com.test.shileiyu.jetpack.ui.BitmapActivity;
import com.test.shileiyu.jetpack.ui.BottomSheetBehaviorActivity;
import com.test.shileiyu.jetpack.ui.CalendarActivity;
import com.test.shileiyu.jetpack.ui.CropActivity;
import com.test.shileiyu.jetpack.ui.DragActivity;
import com.test.shileiyu.jetpack.ui.DragHead2ZoomActivity;
import com.test.shileiyu.jetpack.ui.ItemTouchHelperActivity;
import com.test.shileiyu.jetpack.ui.FlexBoxLayoutActivity;
import com.test.shileiyu.jetpack.ui.LinearSnapActivity;
import com.test.shileiyu.jetpack.ui.MatrixActivity;
import com.test.shileiyu.jetpack.ui.MoveActivity;
import com.test.shileiyu.jetpack.ui.MyBehaviorActivity;
import com.test.shileiyu.jetpack.ui.NestScrooViewRecyActivity;
import com.test.shileiyu.jetpack.ui.NineGirdActivity;
import com.test.shileiyu.jetpack.ui.NumberPickerActivity;
import com.test.shileiyu.jetpack.ui.PageSnapRecyclerViewActivity;
import com.test.shileiyu.jetpack.ui.PermissionActivity;
import com.test.shileiyu.jetpack.ui.PhotoActivity;
import com.test.shileiyu.jetpack.ui.RecyclerViewActivity;
import com.test.shileiyu.jetpack.ui.ScrollConflictActivity;
import com.test.shileiyu.jetpack.ui.ScrollPageActivity;
import com.test.shileiyu.jetpack.ui.ScrollViewViewPagerActivity;
import com.test.shileiyu.jetpack.ui.SeatActivity;
import com.test.shileiyu.jetpack.ui.ServiceActivity;
import com.test.shileiyu.jetpack.ui.SvgActivity;
import com.test.shileiyu.jetpack.ui.TestActivity;
import com.test.shileiyu.jetpack.ui.ViewPageRecyclerActivity;
import com.test.shileiyu.jetpack.ui.home.TabActivity;
import com.test.shileiyu.jetpack.ui.search.SearchActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.main_2_tab, R.id.main_3_tab,
            R.id.main_4_tab, R.id.main_5_tab, R.id.main_6_tab,
            R.id.main_7_tab, R.id.main_8_tab, R.id.main_9_tab
            , R.id.main_10_tab, R.id.main_11_tab
            , R.id.main_13_tab
            , R.id.main_14_tab
            , R.id.main_24_tab
            , R.id.main_25_tab
            , R.id.main_26_tab
            , R.id.main_27_tab
            , R.id.main_28_tab
            , R.id.main_29_tab
            , R.id.main_30_tab
            , R.id.main_31_tab
            , R.id.main_32_tab
            , R.id.main_33_tab
            , R.id.main_34_tab
            , R.id.main_35_tab
            , R.id.main_15_tab, R.id.main_23_tab
            , R.id.main_17_tab, R.id.main_22_tab
            , R.id.main_20_tab, R.id.main_21_tab
            , R.id.main_18_tab, R.id.main_19_tab
            , R.id.main_16_tab
            , R.id.main_12_tab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_2_tab:
                startActivity(new Intent(this, TabActivity.class));
                break;
            case R.id.main_3_tab:
                startActivity(new Intent(this, AreaViewActivity.class));
                break;
            case R.id.main_4_tab:
                startActivity(new Intent(this, SeatActivity.class));
                break;
            case R.id.main_5_tab:
                startActivity(new Intent(this, MatrixActivity.class));
                break;
            case R.id.main_6_tab:
                startActivity(new Intent(this, DragActivity.class));
                break;
            case R.id.main_7_tab:
                startActivity(new Intent(this, MoveActivity.class));
                break;
            case R.id.main_8_tab:
                startActivity(new Intent(this, TestActivity.class));
                break;
            case R.id.main_9_tab:
                startActivity(new Intent(this, NineGirdActivity.class));
                break;
            case R.id.main_10_tab:
                startActivity(new Intent(this, PhotoActivity.class));
                break;
            case R.id.main_11_tab:
                startActivity(new Intent(this, CropActivity.class));
            case R.id.main_12_tab:
                startActivity(new Intent(this, ActivityLifeCycleActivity.class));
                break;
            case R.id.main_13_tab:
                startActivity(new Intent(this, BehaviorActivity.class));
                break;
            case R.id.main_14_tab:
                startActivity(new Intent(this, SvgActivity.class));
                break;
            case R.id.main_15_tab:
                startActivity(new Intent(this, CalendarActivity.class));
                break;
            case R.id.main_16_tab:
                startActivity(new Intent(this, MyBehaviorActivity.class));
                break;
            case R.id.main_17_tab:
                startActivity(new Intent(this, SearchActivity.class));
                break;
            case R.id.main_18_tab:
                startActivity(new Intent(this, BitmapActivity.class));
                break;
            case R.id.main_19_tab:
                startActivity(new Intent(this, DragHead2ZoomActivity.class));
                break;
            case R.id.main_20_tab:
                startActivity(new Intent(this, ServiceActivity.class));
                break;
            case R.id.main_21_tab:
                startActivity(new Intent(this, NumberPickerActivity.class));
                break;
            case R.id.main_22_tab:
                startActivity(new Intent(this, AppBarLayoutActivity.class));
                break;
            case R.id.main_23_tab:
                startActivity(new Intent(this, BottomSheetBehaviorActivity.class));
                break;
            case R.id.main_24_tab:
                startActivity(new Intent(this, ScrollConflictActivity.class));
                break;
            case R.id.main_25_tab:
                startActivity(new Intent(this, ScrollPageActivity.class));
                break;
            case R.id.main_26_tab:
                startActivity(new Intent(this, NestScrooViewRecyActivity.class));
                break;
            case R.id.main_27_tab:
                startActivity(new Intent(this, ViewPageRecyclerActivity.class));
                break;
            case R.id.main_28_tab:
                startActivity(new Intent(this, FlexBoxLayoutActivity.class));
                break;
            case R.id.main_29_tab:
                startActivity(new Intent(this, RecyclerViewActivity.class));
                break;
            case R.id.main_30_tab:
                startActivity(new Intent(this, ItemTouchHelperActivity.class));
                break;
            case R.id.main_31_tab:
                startActivity(new Intent(this, PageSnapRecyclerViewActivity.class));
                break;
            case R.id.main_32_tab:
                startActivity(new Intent(this, LinearSnapActivity.class));
                break;
            case R.id.main_33_tab:
                startActivity(new Intent(this, ScrollViewViewPagerActivity.class));
                break;
            case R.id.main_35_tab:
                startActivity(new Intent(this, PermissionActivity.class));
                break;
            case R.id.main_34_tab:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("damai://ticklet?key=这是一个key"));
                intent.putExtra("extrame", "Test extra");
                intent.putExtra("obj", new Bean("测试对象"));
                ResolveInfo info = getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
                if (info != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "未找到", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }

    }
}
