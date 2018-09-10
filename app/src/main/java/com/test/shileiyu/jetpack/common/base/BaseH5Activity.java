package com.test.shileiyu.jetpack.common.base;

import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.util.OutLineH5Manager;

import butterknife.BindView;

public class BaseH5Activity extends BaseActivity {

    @BindView(R.id.activity_h5_web)
    WebView mWebView;

    @Override
    protected void initView(Bundle savedInstanceState) {
        mWebView.setWebViewClient(new WebViewClient() {
            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                WebResourceResponse response = OutLineH5Manager.singleton().shouldInterceptRequest(view, url);
                if (response != null) {
                    return response;
                }
                return super.shouldInterceptRequest(view, url);
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Nullable
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                WebResourceResponse response = OutLineH5Manager.singleton().shouldInterceptRequest(view, request);
                if (response != null) {
                    return response;
                }
                return super.shouldInterceptRequest(view, request);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_h5_base;
    }
}
