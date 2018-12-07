package com.app.baselib.mask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.app.baselib.AdapterUtils;

/**
 * The type Test mask view.
 * 蒙版测试类
 */
public class TestMaskView extends ImageView {
	
	/**
	 * Instantiates a new Test mask view.
	 *
	 * @param context the context
	 */
	public TestMaskView(Context context) {
		super(context);
	}
	
	/**
	 * Instantiates a new Test mask view.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	public TestMaskView(Context context,@Nullable AttributeSet attrs) {
		super(context,attrs);
	}
	
	/**
	 * Instantiates a new Test mask view.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 * @param defStyleAttr the def style attr
	 */
	public TestMaskView(Context context,@Nullable AttributeSet attrs,int defStyleAttr) {
		super(context,attrs,defStyleAttr);
	}
	
	/**
	 * Instantiates a new Test mask view.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 * @param defStyleAttr the def style attr
	 * @param defStyleRes the def style res
	 */
	@SuppressLint("NewApi")
	public TestMaskView(
			Context context,@Nullable AttributeSet attrs,int defStyleAttr,int defStyleRes)
	{
		super(context,attrs,defStyleAttr,defStyleRes);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		//非常简单的一行代码搞定，给图片添加遮挡蒙版
		int wi = width / 4;
		int hei = height / 4;
		int autoSize = AdapterUtils.dp2px(getContext(),10);
		MaskLoader.builder(this).roundRect(wi,hei,wi * 3,hei * 3,autoSize)
		          .backgroundColor(Color.BLACK).build().draw(canvas);
	}
}