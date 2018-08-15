package com.test.shileiyu.jetpack.common.http;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author shilei.yu
 * @date 2018/8/8
 */

public class ClientGetter {

    private ClientGetter() {
    }

    public static OkHttpClient get() {

        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        Request request = chain.request();
                        HttpUrl newUrl = request.url().newBuilder()
                                .addQueryParameter("device", "android")
                                .addQueryParameter("version", "6.5.9")
                                .build();
                        Request build = request.newBuilder().url(newUrl).build();
                        return chain.proceed(build);
                    }
                })
                .build();
    }
}
