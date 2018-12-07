package com.app.baselib.rx;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 26/6/18
 * @desc 发射器
 */
public interface RxBindEmitter<D> {
    void onStart(D data);

    void onComplete(D data);

    void onError(D data);
}
