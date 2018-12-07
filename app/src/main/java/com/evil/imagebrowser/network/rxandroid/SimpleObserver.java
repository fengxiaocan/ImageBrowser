package com.evil.imagebrowser.network.rxandroid;


import com.app.baselib.log.LogUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 20/9/18
 * @desc ...
 */
public abstract class SimpleObserver<T> implements Observer<T> {
	
	@Override
	public void onSubscribe(Disposable d) {
	
	}
	
	@Override
	public void onError(Throwable e) {
		LogUtils.e("noah",e.getMessage());
	}
	
	@Override
	public void onComplete() {
	
	}
}
