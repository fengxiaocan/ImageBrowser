package com.app.baselib.rx;

import android.view.View;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 26/6/18
 * @desc 接收发射器发射的接收器
 */
public class RxSimpleBindAcceptor<V extends View,T> implements RxBindAcceptor<V,T> {

    @Override
    public void onStart(V view,T data) {

    }

    @Override
    public void onError(V view,T data) {

    }

    @Override
    public void onComplete(V view,T data) {

    }
}
