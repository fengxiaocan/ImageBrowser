package com.app.baselib.rx;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 27/8/18
 * @desc ...
 */
public abstract class DataRxSubscribe<D,T> implements RxSubscriber<T> {
	private D data;
	
	public DataRxSubscribe(D data) {
		this.data = data;
	}
	
	public D getData() {
		return data;
	}
}
