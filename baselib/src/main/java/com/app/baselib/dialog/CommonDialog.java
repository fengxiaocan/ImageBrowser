package com.app.baselib.dialog;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.app.baselib.AdapterUtils;

/**
 * Created: AriesHoo on 2017-01-19 14:16
 * Function: 自定义AlertDialog 弹出提示框
 * Desc:
 */

public class CommonDialog extends Dialog {
	private int width = WindowManager.LayoutParams.WRAP_CONTENT;
	private int height = WindowManager.LayoutParams.WRAP_CONTENT;
	private OnDispatchTouchEventListener mTouchEventListener;
	
	private CommonDialog(Context context) {
		super(context);
	}
	
	private CommonDialog(
			Context context,boolean cancelable,OnCancelListener cancelListener)
	{
		super(context,cancelable,cancelListener);
	}
	
	private CommonDialog(Context context,int themeResId) {
		super(context,themeResId);
	}
	
	public static Builder builder(Context context) {
		return new Builder(context);
	}
	
	public static Builder builder(Context context,int themeResId) {
		return new Builder(context,themeResId);
	}
	
	protected CommonDialog setDispatchTouchEventListener(
			OnDispatchTouchEventListener touchEventListener)
	{
		mTouchEventListener = touchEventListener;
		return this;
	}
	
	@Override
	public boolean dispatchTouchEvent(@NonNull MotionEvent ev)
	{
		if (mTouchEventListener != null) {
			mTouchEventListener.dispatchTouchEvent(ev);
		}
		return super.dispatchTouchEvent(ev);
	}
	
	@Override
	public void show() {
		super.show();
		Window window = getWindow();
		window.setLayout(width,height);
	}
	
	public static class Builder {
		private CommonDialog dialog;
		
		private Builder(Context context) {
			dialog = new CommonDialog(context);
		}
		
		private Builder(Context context,int themeResId) {
			dialog = new CommonDialog(context,themeResId);
		}
		
		public Builder setContentView(View view) {
			dialog.setContentView(view);
			return this;
		}
		
		public Builder setDispatchTouchEventListener(
				OnDispatchTouchEventListener touchEventListener)
		{
			dialog.setDispatchTouchEventListener(touchEventListener);
			return this;
		}
		
		public Builder setContentView(int layoutId) {
			dialog.setContentView(layoutId);
			return this;
		}
		
		public Builder findViewById(IFindViewCallback callback) {
			callback.findView(dialog);
			return this;
		}
		
		/**
		 * 设置窗口透明度
		 *
		 * @param alpha the alpha
		 * @return ios dialog
		 */
		public Builder setAlpha(float alpha) {
			Window window = dialog.getWindow();
			WindowManager.LayoutParams params = window.getAttributes();
			params.alpha = alpha;
			window.setAttributes(params);
			return this;
		}
		
		/**
		 * 设置背景黑暗度
		 *
		 * @param dimAmount the dim amount
		 * @return ios dialog
		 */
		public Builder setDimAmount(float dimAmount) {
			Window window = dialog.getWindow();
			window.setDimAmount(dimAmount);
			return this;
		}
		
		public Builder layout(int width,int height) {
			dialog.width = width;
			dialog.height = height;
			return this;
		}
		
		public Builder background(Drawable drawable) {
			Window window = dialog.getWindow();
			window.setBackgroundDrawable(drawable);
			return this;
		}
		
		public Builder background(@DrawableRes int resId) {
			Window window = dialog.getWindow();
			window.setBackgroundDrawable(dialog.getContext().getResources().getDrawable(resId));
			return this;
		}
		
		public Builder gravity(int gravity) {
			Window window = dialog.getWindow();
			window.setGravity(gravity);
			return this;
		}
		
		public Builder matchWidth() {
			dialog.width = AdapterUtils.getScreenWidth();
			background(null);
			return this;
		}
		
		public Builder padding(int left,int top,int right,int bottom) {
			Window window = dialog.getWindow();
			window.getDecorView().setPadding(left,top,right,bottom);
			return this;
		}
		
		
		public Builder matchHeight() {
			dialog.height = AdapterUtils.getScreenHeight();
			background(null);
			return this;
		}
		
		public Builder matchScreen() {
			dialog.width = AdapterUtils.getScreenWidth();
			dialog.height = AdapterUtils.getScreenHeight();
			background(null);
			return this;
		}
		
		
		/**
		 * 是否设置点击dialog区域外，dialog消失
		 *
		 * @param cancel the cancel
		 */
		public Builder setCanceledOnTouchOutside(boolean cancel) {
			dialog.setCanceledOnTouchOutside(cancel);
			return this;
		}
		
		/**
		 * 设置标题
		 *
		 * @param title the title
		 * @return ios dialog
		 */
		public Builder setTitle(CharSequence title) {
			dialog.setTitle(title);
			return this;
		}
		
		/**
		 * Set title ios dialog.
		 *
		 * @param title the title
		 * @return the ios dialog
		 */
		public Builder setTitle(int title) {
			dialog.setTitle(title);
			return this;
		}
		
		/**
		 * 设置返回键弹框是否可消失
		 *
		 * @param cancel the cancel
		 * @return ios dialog
		 */
		public Builder setCancelable(boolean cancel) {
			dialog.setCancelable(cancel);
			return this;
		}
		
		/**
		 * Set on key listener ios dialog.
		 *
		 * @param onKeyListener the on key listener
		 * @return the ios dialog
		 */
		public Builder setOnKeyListener(
				OnKeyListener onKeyListener)
		{
			dialog.setOnKeyListener(onKeyListener);
			return this;
		}
		
		/**
		 * Set on dismiss listener ios dialog.
		 *
		 * @param onDismissListener the on dismiss listener
		 * @return the ios dialog
		 */
		public Builder setOnDismissListener(
				OnDismissListener onDismissListener)
		{
			dialog.setOnDismissListener(onDismissListener);
			return this;
		}
		
		/**
		 * Show.
		 */
		public CommonDialog show() {
			dialog.show();
			return dialog;
		}
		
		/**
		 * Show.
		 */
		public CommonDialog create() {
			return dialog;
		}
		
		/**
		 * Dismiss.
		 */
		public CommonDialog dismiss() {
			dialog.dismiss();
			return dialog;
		}
	}
}
