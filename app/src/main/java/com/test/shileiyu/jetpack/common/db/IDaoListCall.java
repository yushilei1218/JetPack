package com.test.shileiyu.jetpack.common.db;

import com.j256.ormlite.dao.Dao;

import java.util.List;

/**
 * @author lanche.ysl
 * @date 2018/10/15 下午6:30
 */
public interface IDaoListCall<T> {
    List<T> call(Dao<T, ?> dao);
}
