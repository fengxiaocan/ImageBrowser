package com.app.baselib;

import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;

import com.app.baselib.util.ScreenUtils;

/**
 * 今日头条适配方案
 * 通过修改系统参数来适配android设备
 */
public class AdapterUtils {
	public static final float DESIGN_WIDTH = 360;//设计图宽度
	public static final float DESIGN_HEIGHT = 700;//设计图高度
	private static float appDensity;
	private static float appScaledDensity;
	private static DisplayMetrics appDisplayMetrics;
	private static int barHeight;
	private static int SCREEN_WIDTH = ScreenUtils.getScreenWidth();
	private static int SCREEN_HEIGHT = ScreenUtils.getScreenHeight();
	
	/**
	 * 在Application里初始化一下
	 *
	 * @param application
	 */
	public static void initDensity(@NonNull final Application application) {
		//获取application的DisplayMetrics
		appDisplayMetrics = application.getResources().getDisplayMetrics();
		//获取状态栏高度
		barHeight = getStatusBarHeight(application);
		
		if (appDensity == 0) {
			//初始化的时候赋值
			appDensity = appDisplayMetrics.density;
			appScaledDensity = appDisplayMetrics.scaledDensity;
			//添加字体变化的监听
			application.registerComponentCallbacks(new ComponentCallbacks() {
				@Override
				public void onConfigurationChanged(Configuration newConfig) {
					//字体改变后,将appScaledDensity重新赋值
					if (newConfig != null && newConfig.fontScale > 0) {
						appScaledDensity = application.getResources()
						                              .getDisplayMetrics().scaledDensity;
					}
				}
				
				@Override
				public void onLowMemory() {
				}
			});
		}
	}
	
	/**
	 * 此方法在BaseActivity中做初始化(如果不封装BaseActivity的话,直接用下面那个方法就好)
	 * 在setContentView()之前设置
	 *
	 * @param activity
	 */
	public static void adapterDefault(Context activity) {
		adaptActivity(activity,false);
	}
	
	/**
	 * targetDensity
	 * targetScaledDensity
	 * targetDensityDpi
	 * 这三个参数是统一修改过后的值
	 * orientation:方向值是否以高度为准
	 */
	public static void adaptActivity(@Nullable Context context,boolean isBaseHeight) {
		adaptActivity(context,isBaseHeight,isBaseHeight ? DESIGN_HEIGHT : DESIGN_WIDTH);
	}
	
	/**
	 * 适配当前Activity
	 *
	 * @param context 当前的上下文
	 * @param isBaseHeight 是否基于高度适配
	 * @param size 以高度为适配则传入设计图高度,以宽度适配则传入设计图宽度
	 */
	public static void adaptActivity(@Nullable Context context,boolean isBaseHeight,float size) {
		float targetDensity;
		if (isBaseHeight) {
			targetDensity = (appDisplayMetrics.heightPixels - barHeight) / size;//设计图的高度 单位:dp
		}
		else {
			targetDensity = appDisplayMetrics.widthPixels / size;//设计图的宽度 单位:dp
		}
		
		float targetScaledDensity = targetDensity * (appScaledDensity / appDensity);
		int targetDensityDpi = (int)(160 * targetDensity);
		
		/**
		 *
		 * 最后在这里将修改过后的值赋给系统参数
		 * 只修改Activity的density值
		 */
		DisplayMetrics activityDisplayMetrics = context.getResources().getDisplayMetrics();
		activityDisplayMetrics.density = targetDensity;
		activityDisplayMetrics.scaledDensity = targetScaledDensity;//修改字体适配
		activityDisplayMetrics.densityDpi = targetDensityDpi;
	}
	
	/**
	 * 获取状态栏高度
	 *
	 * @param context
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources()
		                        .getIdentifier("status_bar_height","dimen","android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}
	
	/**
	 * 获取状态栏高度
	 *
	 * @return
	 */
	public static int getStatusBarHeight() {
		return barHeight;
	}
	
	/**
	 * 取消适配
	 * 用于系统级的Dialog显示前调用
	 */
	public static void cancelAdaptScreen(Context context) {
		final DisplayMetrics systemDm = Resources.getSystem().getDisplayMetrics();
		if (context != null) {
			DisplayMetrics activityDm = context.getResources().getDisplayMetrics();
			activityDm.density = systemDm.density;
			activityDm.scaledDensity = systemDm.scaledDensity;
			activityDm.densityDpi = systemDm.densityDpi;
		}
	}
	
	public static float getDensity(Context context) {
		DisplayMetrics activityDisplayMetrics = context.getResources().getDisplayMetrics();
		float scale = activityDisplayMetrics.density;
		return scale;
	}
	
	/**
	 * dp转px
	 *
	 * @param dpValue dp值
	 * @return px值
	 */
	public static int dp2px(Context context,float dpValue) {
		return (int)(dpValue * getDensity(context) + 0.5f);
	}
	
	/**
	 * px转dp
	 *
	 * @param pxValue px值
	 * @return dp值
	 */
	public static int px2dp(Context context,float pxValue) {
		return (int)(pxValue / getDensity(context) + 0.5f);
	}
	
	/**
	 * sp转px
	 *
	 * @param spValue sp值
	 * @return px值
	 */
	public static int sp2px(Context context,float spValue) {
		DisplayMetrics activityDisplayMetrics = context.getResources().getDisplayMetrics();
		final float fontScale = activityDisplayMetrics.scaledDensity;
		return (int)(spValue * fontScale + 0.5f);
	}
	
	/**
	 * px转sp
	 *
	 * @param pxValue px值
	 * @return sp值
	 */
	public static int px2sp(Context context,float pxValue) {
		DisplayMetrics activityDisplayMetrics = context.getResources().getDisplayMetrics();
		final float fontScale = activityDisplayMetrics.scaledDensity;
		return (int)(pxValue / fontScale + 0.5f);
	}
	
	public static int getScreenWidth() {
		return SCREEN_WIDTH;
	}
	
	public static int getScreenHeight() {
		return SCREEN_HEIGHT;
	}
}
