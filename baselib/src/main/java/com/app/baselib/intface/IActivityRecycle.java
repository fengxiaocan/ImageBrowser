package com.app.baselib.intface;

/**
 * The interface Activity recycle.
 * 实现该接口，将会在activity的onDestroy方法中做销毁操作
 */
public interface IActivityRecycle {
    /**
     * On recycle.
     */
    void onRecycle();
}
