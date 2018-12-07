package com.app.baselib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.app.baselib.AdapterUtils;
import com.app.baselib.R;
import com.app.baselib.drawable.ShadowDrawable;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 5/11/18
 * @desc ...
 */
public class ShadowView extends View {
	
	public ShadowView(Context context) {
		this(context,null);
	}
	
	public ShadowView(
			Context context,@Nullable AttributeSet attrs)
	{
		this(context,attrs,0);
	}
	
	public ShadowView(
			Context context,@Nullable AttributeSet attrs,int defStyleAttr)
	{
		super(context,attrs,defStyleAttr);
		init(attrs);
	}
	
	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public ShadowView(
			Context context,@Nullable AttributeSet attrs,int defStyleAttr,int defStyleRes)
	{
		super(context,attrs,defStyleAttr,defStyleRes);
		init(attrs);
	}
	
	private void init(AttributeSet attrs) {
		setWillNotDraw(false);
		TypedArray typedArray = getContext().obtainStyledAttributes(attrs,R.styleable.ShadowView);
		int color = typedArray.getColor(R.styleable.ShadowView_shadowColor,Color.GRAY);
		int shapeColor = typedArray.getColor(R.styleable.ShadowView_shapeColor,Color.WHITE);
		int shadowRadius = (int)typedArray.getDimension(R.styleable.ShadowView_shadowRadius,
		                                                AdapterUtils.dp2px(getContext(),5));
		int shapeRadius = (int)typedArray.getDimension(R.styleable.ShadowView_shapeRadius,
		                                               AdapterUtils.dp2px(getContext(),8));
		setPadding(shadowRadius,shadowRadius,shadowRadius,shadowRadius);
		ShadowDrawable
				.setShadowDrawable(this,ShadowDrawable.SHAPE_ROUND,shapeColor,shapeRadius,
				                   color,shadowRadius,0,0);
	}
}
