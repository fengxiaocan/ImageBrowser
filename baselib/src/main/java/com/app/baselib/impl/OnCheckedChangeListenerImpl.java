package com.app.baselib.impl;

import android.widget.CompoundButton;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 29/10/18
 * @desc ...
 */
public abstract class OnCheckedChangeListenerImpl<T> implements CompoundButton.OnCheckedChangeListener {
	private T data;
	
	public OnCheckedChangeListenerImpl(T data) {
		this.data = data;
	}
	
	public T getData() {
		return data;
	}
}
