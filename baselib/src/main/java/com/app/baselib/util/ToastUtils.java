package com.app.baselib.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Looper;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * <pre>
 *
 *
 *     time  : 2016/09/29
 *     desc  : 吐司相关工具类
 * </pre>
 */
public final class ToastUtils {
	
	private static Toast sToast;
	private static int gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
	private static int xOffset = 0;
	private static int yOffset = (int)(64 * Utils.getResources().getDisplayMetrics().density + 0.5);
	@SuppressLint("StaticFieldLeak")
	private static View customView;
	private static TextView mMessage;
	
	private ToastUtils() {
		throw new UnsupportedOperationException("u can't instantiate me...");
	}
	
	
	/**
	 * 设置吐司位置
	 *
	 * @param gravity 位置
	 * @param xOffset x偏移
	 * @param yOffset y偏移
	 */
	public static void setGravity(int gravity,int xOffset,int yOffset) {
		ToastUtils.gravity = gravity;
		ToastUtils.xOffset = xOffset;
		ToastUtils.yOffset = yOffset;
	}
	
	/**
	 * 设置吐司view
	 *
	 * @param layoutId 视图
	 */
	public static void setView(@LayoutRes int layoutId) {
		LayoutInflater inflate = (LayoutInflater)Utils.getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		ToastUtils.customView = inflate.inflate(layoutId,null);
	}
	
	
	/**
	 * 设置吐司view
	 *
	 * @param layoutId 视图
	 */
	public static void setView(@LayoutRes int layoutId,int textViewID) {
		setView(layoutId);
		mMessage = (TextView)customView.findViewById(textViewID);
	}
	
	/**
	 * 设置吐司view
	 *
	 * @param view 视图
	 */
	public static void setView(View view,int textViewID) {
		ToastUtils.customView = view;
		if (customView != null) {
			mMessage = (TextView)customView.findViewById(textViewID);
		}
		else {
			mMessage = null;
		}
	}
	
	/**
	 * 设置吐司view
	 *
	 * @param view 视图
	 */
	public static void setView(View view,TextView textMessage) {
		ToastUtils.customView = view;
		ToastUtils.mMessage = textMessage;
	}
	
	/**
	 * 获取吐司view
	 *
	 * @return view 自定义view
	 */
	public static View getView() {
		if (customView != null) {
			return customView;
		}
		if (sToast != null) {
			return sToast.getView();
		}
		return null;
	}
	
	/**
	 * 设置吐司view
	 *
	 * @param view 视图
	 */
	public static void setView(View view) {
		ToastUtils.customView = view;
	}
	
	
	public static boolean isRunOnMain() {
		return Looper.myLooper() == Looper.getMainLooper();
	}
	
	
	/**
	 * 显示短时吐司
	 *
	 * @param text 文本
	 */
	public static void showShort(CharSequence text) {
		show(text,Toast.LENGTH_SHORT);
	}
	
	/**
	 * 显示短时吐司
	 *
	 * @param resId 资源Id
	 */
	public static void showShort(@StringRes int resId) {
		show(resId,Toast.LENGTH_SHORT);
	}
	
	/**
	 * 显示短时吐司
	 *
	 * @param resId 资源Id
	 * @param args 参数
	 */
	public static void showShort(@StringRes int resId,Object... args) {
		show(resId,Toast.LENGTH_SHORT,args);
	}
	
	/**
	 * 显示短时吐司
	 *
	 * @param format 格式
	 * @param args 参数
	 */
	public static void showShort(String format,Object... args) {
		show(format,Toast.LENGTH_SHORT,args);
	}
	
	/**
	 * 显示长时吐司
	 *
	 * @param text 文本
	 */
	public static void showLong(CharSequence text) {
		show(text,Toast.LENGTH_LONG);
	}
	
	/**
	 * 显示长时吐司
	 *
	 * @param resId 资源Id
	 */
	public static void showLong(@StringRes int resId) {
		show(resId,Toast.LENGTH_LONG);
	}
	
	/**
	 * 显示长时吐司
	 *
	 * @param resId 资源Id
	 * @param args 参数
	 */
	public static void showLong(@StringRes int resId,Object... args) {
		show(resId,Toast.LENGTH_LONG,args);
	}
	
	/**
	 * 显示长时吐司
	 *
	 * @param format 格式
	 * @param args 参数
	 */
	public static void showLong(String format,Object... args) {
		show(format,Toast.LENGTH_LONG,args);
	}
	
	/**
	 * 显示吐司
	 *
	 * @param resId 资源Id
	 * @param duration 显示时长
	 */
	private static void show(@StringRes int resId,int duration) {
		show(Utils.getResources().getText(resId).toString(),duration);
	}
	
	/**
	 * 显示吐司
	 *
	 * @param resId 资源Id
	 * @param duration 显示时长
	 * @param args 参数
	 */
	private static void show(@StringRes int resId,int duration,Object... args) {
		show(String.format(Utils.getResources().getString(resId),args),duration);
	}
	
	/**
	 * 显示吐司
	 *
	 * @param format 格式
	 * @param duration 显示时长
	 * @param args 参数
	 */
	private static void show(String format,int duration,Object... args) {
		show(String.format(format,args),duration);
	}
	
	/**
	 * 显示吐司
	 *
	 * @param text 文本
	 * @param duration 显示时长
	 */
	private static void show(final CharSequence text,final int duration) {
		if (isRunOnMain()) {
			cancel();
			if (customView != null) {
				sToast = new Toast(Utils.getContext());
				sToast.setView(customView);
				if (mMessage != null) {
					mMessage.setText(text);
				}
				sToast.setDuration(duration);
			}
			else {
				sToast = Toast.makeText(Utils.getContext(),text,duration);
			}
			sToast.setGravity(gravity,xOffset,yOffset);
			sToast.show();
		}else {
			HandlerUtils.getHandler().post(new Runnable() {
				@Override
				public void run() {
					cancel();
					if (customView != null) {
						sToast = new Toast(Utils.getContext());
						sToast.setView(customView);
						if (mMessage != null) {
							mMessage.setText(text);
						}
						sToast.setDuration(duration);
					}
					else {
						sToast = Toast.makeText(Utils.getContext(),text,duration);
					}
					sToast.setGravity(gravity,xOffset,yOffset);
					sToast.show();
				}
			});
		}
	}
	
	/**
	 * 取消吐司显示
	 */
	public static void cancel() {
		if (sToast != null) {
			sToast.cancel();
		}
		sToast = null;
	}
}