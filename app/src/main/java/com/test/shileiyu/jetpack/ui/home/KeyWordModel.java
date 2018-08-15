package com.test.shileiyu.jetpack.ui.home;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.test.shileiyu.jetpack.common.base.AbsModel;
import com.test.shileiyu.jetpack.common.bean.KeyWord;
import com.test.shileiyu.jetpack.common.bean.KeyWordCategory;
import com.test.shileiyu.jetpack.common.bean.Result;
import com.test.shileiyu.jetpack.common.bean.SubTab;
import com.test.shileiyu.jetpack.common.http.NetWork;
import com.test.shileiyu.jetpack.common.http.ProxySubscriber;
import com.test.shileiyu.jetpack.common.util.Util;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author shilei.yu
 * @date 2018/8/10
 */

public class KeyWordModel extends AbsModel {
    private final List<KeyWordCategory> total = new ArrayList<>();
    private final List<KeyWord> keySelected = new ArrayList<>();

    private SubTab mTab;

    public KeyWordModel(SubTab tab) {
        mTab = tab;
    }

    MutableLiveData<List<KeyWordCategory>> mKeyWordLiveData = new MutableLiveData<>();

    MutableLiveData<KeyWordCategory> mCategoryLiveData = new MutableLiveData<>();

    MutableLiveData<List<KeyWord>> mKeyChangedLiveData = new MutableLiveData<>();

    @SuppressLint("CheckResult")
    public void getKeyWords() {
        NetWork.sApi.getSpecKeyWords(mTab.keywordId).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProxySubscriber<Result<List<KeyWordCategory>>>(this, "请求KeyWord") {
                    @Override
                    public void onSuccess(@NonNull Result<List<KeyWordCategory>> data) {
                        Util.showToast("keyWord 成功");
                        initKeyWord(data.data);

                        mKeyWordLiveData.setValue(total);
                        mKeyChangedLiveData.setValue(getKey());
                    }

                    @Override
                    public void onFail(int code, String msg) {
                        Util.showToast("keyWord 失败");
                    }
                });
    }

    private void initKeyWord(List<KeyWordCategory> data) {
        for (KeyWordCategory k : data) {
            List<KeyWord> metadataValues = k.metadataValues;
            if (metadataValues == null) {
                k.metadataValues = new ArrayList<>();
            }
            KeyWord element = new KeyWord(k);
            element.isSelect = true;
            k.metadataValues.add(0, element);

            for (int i = 0; i < k.metadataValues.size(); i++) {
                k.metadataValues.get(i).parent = k;
            }

        }
        total.addAll(data);
    }

    public void onKeyWordSelect(KeyWord keyWord) {
        if (keyWord.isSelect) {
            return;
        }
        if (keyWord.parent != null) {
            List<KeyWord> metadataValues = keyWord.parent.metadataValues;
            if (metadataValues == null) {
                return;
            }
            for (KeyWord key : metadataValues) {
                key.isSelect = false;
            }
            if (metadataValues.contains(keyWord)) {
                keyWord.isSelect = true;
            } else {
                metadataValues.get(0).isSelect = true;
            }
            mCategoryLiveData.setValue(keyWord.parent);
            mKeyChangedLiveData.setValue(getKey());
        }
    }

    private List<KeyWord> getKey() {
        keySelected.clear();
        if (total.size() > 0) {
            for (int i = 0; i < total.size(); i++) {
                KeyWordCategory keyWordCategory = total.get(i);
                List<KeyWord> metadataValues = keyWordCategory.metadataValues;
                if (metadataValues != null) {
                    for (int j = 0; j < metadataValues.size(); j++) {
                        KeyWord keyWord = metadataValues.get(j);
                        if (keyWord.isSelect) {
                            keySelected.add(keyWord);
                        }
                    }
                }
            }
        }
        return keySelected;
    }
}
