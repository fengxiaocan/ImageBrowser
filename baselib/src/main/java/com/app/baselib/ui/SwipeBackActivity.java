package com.app.baselib.ui;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.app.baselib.back.SwipeBackAdapter;

/**
 * The type Swipe back activity.
 *
 * @项目名： BaseApp
 * @包名： com.dgtle.baselib.base.ui
 * @创建者: Noah.冯
 * @时间: 11 :19
 * @描述： 可以侧滑的activity
 */
public class SwipeBackActivity extends BasicActivity {
	public static Typeface typeface2;
	/**
	 * The M adapter.
	 */
	public SwipeBackAdapter mAdapter;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isCanSwipeBack()) {
			getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
			mAdapter = new SwipeBackAdapter(this);
		}
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		if (mAdapter != null) {
			mAdapter.onPostCreate();
		}
	}
	
	/**
	 * 设置滑动方向
	 *
	 * @param flags the flags
	 */
	public void setSwipeFlags(SwipeBackAdapter.SwipeFlags flags) {
		mAdapter.setSwipeFlags(flags);
	}
	
	@Override
	public <T extends View> T findViewById(int id) {
		T v = ((T)super.findViewById(id));
		if (v == null && mAdapter != null) { return mAdapter.findViewById(id); }
		return v;
	}
	
	/**
	 * Is can swipe back boolean.是否能侧滑返回
	 *
	 * @return the boolean
	 */
	public boolean isCanSwipeBack() {
		return true;
	}
}
