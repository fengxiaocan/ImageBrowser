package com.app.baselib.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

public class DrawView extends View {
	public DrawView(Context context) {
		super(context);
		setWillNotDraw(false);
	}
	
	public DrawView(
			Context context, @Nullable AttributeSet attrs)
	{
		super(context,attrs);
		setWillNotDraw(false);
	}
	
	public DrawView(
			Context context, @Nullable AttributeSet attrs,
			int defStyleAttr)
	{
		super(context,attrs,defStyleAttr);
		setWillNotDraw(false);
	}
	
	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public DrawView(
			Context context, @Nullable AttributeSet attrs,
			int defStyleAttr,int defStyleRes)
	{
		super(context,attrs,defStyleAttr,defStyleRes);
		setWillNotDraw(false);
	}
}
