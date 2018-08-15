package com.test.shileiyu.jetpack.common.http;

import com.test.shileiyu.jetpack.common.bean.Album;
import com.test.shileiyu.jetpack.common.bean.HttpResult;
import com.test.shileiyu.jetpack.common.bean.KeyWordCategory;
import com.test.shileiyu.jetpack.common.bean.Result;
import com.test.shileiyu.jetpack.common.bean.TabResult;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author shilei.yu
 * @date 2018/8/8
 */

public class NetWork {
    public static Api sApi;

    static {
        Retrofit retrofit = new Retrofit.Builder()
                .client(ClientGetter.get())
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .baseUrl("http://mobile.ximalaya.com")
                .build();
        sApi = retrofit.create(Api.class);
    }

    public interface Api {
        @GET("/mobile/discovery/v1/rank/album")
        Flowable<HttpResult<List<Album>>> getAlbumList(@Query("pageId") int pageId, @Query("pageSize") int pageSize, @Query("rankingListId") int rankingListId);

        @GET("/mobile/discovery/v1/rank/album")
        Call<HttpResult<Album>> getAlbums(@Query("pageId") int pageId, @Query("pageSize") int pageSize, @Query("rankingListId") int rankingListId);

        @GET("/discovery-category/keyword/all?categoryId=3&channel=and-d3&contentType=album&gender=9")
        Flowable<TabResult> getAllTab();

        @GET("/discovery-category/keyword/metadatas?categoryId=3")
        Flowable<Result<List<KeyWordCategory>>> getSpecKeyWords(@Query("keywordId") int keywordId);

        @GET("/mobile/discovery/v2/category/metadata/albums?appid=0&categoryId=3&deviceId=ffffffff-801e-2f90-ea26-4fbf0037d7ef&network=wifi&operator=3&scale=1")
        Flowable<HttpResult<List<Album>>> get(
                @Query("pageId") int pageId, @Query("pageSize") int pageSize,
                @Query("calcDimension") String calcDimension,
                @Query("metadatas") String metadatas,
                @Query("keywordId") int keywordId,
                @Query("categoryId") int categoryId);

        @GET("/mobile/discovery/v2/category/metadata/albums?appid=0&categoryId=3&deviceId=ffffffff-801e-2f90-ea26-4fbf0037d7ef&network=wifi&operator=3&scale=1")
        Observable<HttpResult<List<Album>>> get1(
                @Query("pageId") int pageId, @Query("pageSize") int pageSize,
                @Query("calcDimension") String calcDimension,
                @Query("metadatas") String metadatas,
                @Query("keywordId") int keywordId,
                @Query("categoryId") int categoryId);
    }
}
