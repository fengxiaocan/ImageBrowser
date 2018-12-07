package com.app.baselib.impl;

/**
 * @项目名： MiParkAdmin
 * @包名： com.vooda.parkadmin.impl
 * @创建者: Noah.冯
 * @时间: 15:23
 * @描述： TODO
 */
public class TRunnable<T> implements Runnable {
    protected T t;

    public TRunnable(T t1) {
        t = t1;
    }

    public T getData(){
        return t;
    }

    @Override
    public void run() {

    }
}
