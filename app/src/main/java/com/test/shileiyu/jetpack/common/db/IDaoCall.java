package com.test.shileiyu.jetpack.common.db;

import com.j256.ormlite.dao.Dao;

/**
 * @author lanche.ysl
 * @date 2018/10/15 下午5:55
 */
public interface IDaoCall<R,P> {
    R call(Dao<P, ?> dao);
}
