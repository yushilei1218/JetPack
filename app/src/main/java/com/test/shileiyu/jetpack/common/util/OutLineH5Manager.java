package com.test.shileiyu.jetpack.common.util;

import android.os.Build;
import android.os.Looper;

import android.support.annotation.RequiresApi;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import com.test.shileiyu.jetpack.BaseApp;

import java.io.IOException;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 1.查询hyid
 * 2.下载解压离线包
 * 3.更新离线包配置
 *
 * @author shilei.yu
 * @date 2018/9/10
 */

public class OutLineH5Manager {
    private OutLineH5Manager() {
    }

    private static OutLineH5Manager sManager;

    private final LocalH5Resource mH5Resource = new LocalH5Resource();

    public static OutLineH5Manager singleton() {
        synchronized (OutLineH5Manager.class) {
            if (sManager == null) {
                sManager = new OutLineH5Manager();
            }
        }
        return sManager;
    }

    public void initInSubThread() {
        Runnable task = new Runnable() {

            @Override
            public void run() {
                mH5Resource.setUpConfigByLocal();
            }
        };
        if (Looper.getMainLooper().getThread().equals(Thread.currentThread())) {
            ThreadPool.submit(task);
        } else {
            task.run();
        }
    }


    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        return mH5Resource.getIfHasLocal(url);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        return mH5Resource.getIfHasLocal(request.getUrl().toString());
    }

    public void checkoutServerH5Config() {
        //如果有更新
        //1.clear map
        //2.触发下载
        //3.下载成功更新map
    }


    private class LocalH5Resource {

        private ConcurrentHashMap<String, String> mUrlPathMap = new ConcurrentHashMap<>();

        boolean hasLocal(String url) {
            return mUrlPathMap.containsKey(url);
        }

        WebResourceResponse getIfHasLocal(String url) {
            if (hasLocal(url)) {
                String localPath = mUrlPathMap.get(url);
                InputStream open = null;
                try {
                    open = BaseApp.sApplication.getAssets().open(localPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (open != null) {
                    String mimeType;
                    if (url.contains("css")) {
                        mimeType = "text/css";
                    } else if (url.contains("jpg")) {
                        mimeType = "image/jpeg";
                    } else {
                        mimeType = "image/png";
                    }
                    return new WebResourceResponse(mimeType, "utf-8", open);
                }
            }
            return null;
        }

        void setUpConfigByLocal() {
            synchronized (this) {
                //查询配置 更新配置
            }
        }

        void reset(Map<String, String> map) {
            clear();
            mUrlPathMap.putAll(map);
        }

        void clear() {
            mUrlPathMap.clear();
        }
    }
}
