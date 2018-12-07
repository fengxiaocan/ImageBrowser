package com.app.baselib.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 29/10/18
 * @desc ...
 */
public class SelectTextView extends TextView {
	public SelectTextView(Context context) {
		super(context);
	}
	
	public SelectTextView(
			Context context, @Nullable AttributeSet attrs)
	{
		super(context,attrs);
	}
	
	public SelectTextView(
			Context context, @Nullable AttributeSet attrs,
			int defStyleAttr)
	{
		super(context,attrs,defStyleAttr);
	}
	
	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public SelectTextView(
			Context context, @Nullable AttributeSet attrs,
			int defStyleAttr,int defStyleRes)
	{
		super(context,attrs,defStyleAttr,defStyleRes);
	}
	
	@Override
	public void setSelected(boolean selected) {
		super.setSelected(selected);
		if (mOnSelectStateChangeListener != null){
			mOnSelectStateChangeListener.onSelect(this,isSelected());
		}
	}
	
	private OnSelectStateChangeListener mOnSelectStateChangeListener;
	
	public void setOnSelectStateChangeListener(
			OnSelectStateChangeListener onSelectStateChangeListener)
	{
		mOnSelectStateChangeListener = onSelectStateChangeListener;
	}
	
	public interface OnSelectStateChangeListener{
		void onSelect(TextView view,boolean select);
	}
}
