package com.test.shileiyu.jetpack.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.base.AbsBaseAdapter;
import com.test.shileiyu.jetpack.common.base.BaseActivity;
import com.test.shileiyu.jetpack.common.bean.Bean;
import com.test.shileiyu.jetpack.common.widget.NineGridLayoutImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

public class NineGirdActivity extends BaseActivity {
    @BindView(R.id.nine_lv)
    ListView mListView;

    @Override
    protected void initView(Bundle savedInstanceState) {
        Adapter adapter = new Adapter();

        List<String> url = new ArrayList<>();
        url.add("http://fdfs.xmcdn.com/group39/M06/01/19/wKgJnlphjGuwslJPAAGfGRV4Y-o110_mobile_small.jpg");
        url.add("http://fdfs.xmcdn.com/group33/M04/0A/E2/wKgJUVof-T3h3PZsAAGDKccTSPI802_mobile_small.jpg");
        url.add("http://fdfs.xmcdn.com/group47/M01/FF/D1/wKgKm1tOkNaxRMI9AAGTAytbYjk229_mobile_small.jpg");
        url.add("http://fdfs.xmcdn.com/group23/M01/15/2B/wKgJL1gYYjzxW7fQAAWaMAvliP8961_mobile_small.jpg");
        url.add("http://fdfs.xmcdn.com/group28/M0A/69/EE/wKgJSFlsKwiQGzwxAASIZK0o4QQ903_mobile_small.jpg");
        url.add("http://fdfs.xmcdn.com/group42/M09/F2/53/wKgJ81rIGyKybEF2AADFbsJjv4o047_mobile_small.jpg");
        url.add("http://fdfs.xmcdn.com/group27/M00/17/4E/wKgJW1jjBm6jBCs4AAVMqSeBxhY460_mobile_small.jpg");
        url.add("http://fdfs.xmcdn.com/group7/M03/64/C6/wKgDX1cxh4vQtrYiAAmBcWMpkhM762_mobile_small.jpg");
        url.add("http://fdfs.xmcdn.com/group18/M04/C5/14/wKgJJVfDl-GS-CPPAAUbRT3EQ6A771_mobile_small.jpg");

        List<Bean> data = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            Bean e = new Bean("http://fdfs.xmcdn.com/group39/M06/01/19/wKgJnlphjGuwslJPAAGfGRV4Y-o110_mobile_small.jpg");
            data.add(e);
            int i1 = random.nextInt(9);
            i1++;
            if (i1 > 9) {
                i1 = 9;
            }
            if (i1 < 0) {
                i1 = 1;
            }

            for (int j = 0; j < i1; j++) {
                e.urls.add(url.get(random.nextInt(9)));
            }

        }
        adapter.data = data;
        mListView.setAdapter(adapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_nine_gird;
    }

    private int index = 0;

    private class Adapter extends AbsBaseAdapter<Bean> {
        @Override
        public VH<Bean> getVH() {
            return new VH<Bean>() {
                @Override
                public View create(Bean item, ViewGroup parent) {
                    return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nine, parent, false);
                }

                @Override
                public void bind(Bean item) {
                    NineGridLayoutImpl nine = itemView.findViewById(R.id.nine_layout);
                    TextView tv = itemView.findViewById(R.id.tv);
                    if (TextUtils.isEmpty(nine.name)) {
                        nine.name = "item" + index++;
                    }
                    int size = item.urls.size();
                    nine.showChildCount(size);
                    String text = "size =" + size;
                    tv.setText(text);

                    RequestOptions placeholder = RequestOptions.placeholderOf(R.mipmap.ic_deafult).centerCrop();

                    List<View> showViews = nine.getShowViews();

                    for (int i = 0; i < size; i++) {
                        ImageView v1 = (ImageView) showViews.get(i);

                        Glide.with(NineGirdActivity.this)
                                .asBitmap().load(item.urls.get(i)).apply(placeholder).into(v1);
                    }
                    RequestOptions placeholder2 = RequestOptions.placeholderOf(R.mipmap.ic_deafult).centerCrop();
                    ImageView img = itemView.findViewById(R.id.img);
                    Glide.with(NineGirdActivity.this).asBitmap().load(item.name)
                            .apply(placeholder2).into(img);
                }
            };
        }
    }
}
