package com.app.baselib.impl;

/**
 * @项目名： MiParkAdmin
 * @包名： com.vooda.parkadmin.impl
 * @创建者: Noah.冯
 * @时间: 15:23
 * @描述： TODO
 */
public abstract class TVRunnable<T,V> implements Runnable {
    protected T t;
    protected V v;

    public TVRunnable(T t, V v) {
        this.t = t;
        this.v = v;
    }
}
