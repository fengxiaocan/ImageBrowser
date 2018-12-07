package com.app.baselib.rx;

import android.view.View;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 26/6/18
 * @desc 接收发射器发射的接收器
 */
public interface RxBindAcceptor<V extends View,T> {
    void onStart(V view,T data);

    void onError(V view,T data);

    void onComplete(V view,T data);
}
