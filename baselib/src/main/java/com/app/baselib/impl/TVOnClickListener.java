package com.app.baselib.impl;

import android.view.View;

/**
 * @项目名： MyComUtils
 * @包名： com.dgtle.baselib.lib.impl
 * @创建者: Noah.冯
 * @时间: 12:01
 * @描述： 双参数类型
 */
public abstract class TVOnClickListener<T,V> implements View.OnClickListener {
    protected T t;
    protected V v;

    public TVOnClickListener(V v, T t) {
        this.v = v;
        this.t = t;
    }
}
