package com.app.baselib.dialog;

import android.support.annotation.NonNull;
import android.view.MotionEvent;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 12/10/18
 * @desc ...
 */
public interface OnDispatchTouchEventListener {
	void dispatchTouchEvent(@NonNull MotionEvent ev);
}
