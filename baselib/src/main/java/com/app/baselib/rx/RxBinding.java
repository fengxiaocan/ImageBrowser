package com.app.baselib.rx;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 26/6/18
 * @desc 发射器
 */
public interface RxBinding<T,V> {
    void onSubscribe(RxBindEmitter<T> emitter);
}
