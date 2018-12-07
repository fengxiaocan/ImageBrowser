package com.app.baselib.manager;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.MetricAffectingSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.app.baselib.log.LogUtils;
import com.app.baselib.util.ViewUtils;

public final class FontRouter {
	public static Typeface typefaceNormal;
	public static Typeface typefaceBold;
	
	
	public static void initFont(Application app) {
		if (typefaceBold == null) {
			typefaceBold = Typeface
					.createFromAsset(app.getAssets(),"font/SourceHanSansCN-Medium.otf");
		}
		if (typefaceNormal == null) {
			typefaceNormal = Typeface
					.createFromAsset(app.getAssets(),"font/SourceHanSansCN-Regular.otf");
		}
	}
	
	public static void attachBaseActivity(AppCompatActivity context) {
		LayoutInflaterCompat.setFactory(LayoutInflater.from(context),
		                                new DelegateLayoutInflaterFactory(context));
	}
	
	public static void boldFont(TextView textView) {
		if (textView != null) {
			textView.setIncludeFontPadding(false);
			textView.setTypeface(typefaceBold);
		}
	}
	
	public static void normlaFont(TextView textView) {
		if (textView != null) {
			textView.setIncludeFontPadding(false);
			textView.setTypeface(typefaceNormal);
		}
	}
	
	public static void changeFont(TextView textView,boolean isBold) {
		if (textView != null) {
			textView.setIncludeFontPadding(false);
			textView.setTypeface(isBold ? typefaceBold : typefaceNormal);
		}
	}
	
	public static void changeFont(boolean isBold,TextView... textViews) {
		for (TextView textView : textViews) {
			if (textView != null) {
				textView.setIncludeFontPadding(false);
				textView.setTypeface(isBold ? typefaceBold : typefaceNormal);
			}
		}
	}
	
	public static void boldFont(TextView ... textViews) {
		for (TextView textView : textViews) {
			if (textView != null) {
				textView.setIncludeFontPadding(false);
				textView.setTypeface(typefaceBold);
			}
		}
	}
	
	public static void changeFont(View view) {
		if (view instanceof TextView) {
			changeFont(((TextView)view));
		}
		else if (view instanceof ViewGroup) {
			changeFont(((ViewGroup)view));
		}
	}
	
	public static void changeFont(TextView textView) {
		changeFont(textView,ViewUtils.isBoldText(textView));
	}
	
	public static void changeFont(Paint paint,boolean isBold) {
		if (paint != null) { paint.setTypeface(isBold ? typefaceBold : typefaceNormal); }
	}
	
	public static void changeFont(Paint paint) {
		if (paint != null) {
			paint.setTypeface(paint.isFakeBoldText() ? typefaceBold : typefaceNormal);
		}
	}
	
	public static void changeFont(ViewGroup viewGroup,boolean isBold) {
		try {
			for (int i = 0;i < viewGroup.getChildCount();i++) {
				View v = viewGroup.getChildAt(i);
				if (v instanceof ViewGroup) {
					changeFont((ViewGroup)v,isBold);
				}
				else if (v instanceof TextView) {
					changeFont(((TextView)v),isBold);
				}
			}
		} catch (Exception e) {
			LogUtils.e("font",e.getMessage());
		}
	}
	
	public static void changeFont(ViewGroup viewGroup) {
		try {
			for (int i = 0;i < viewGroup.getChildCount();i++) {
				View v = viewGroup.getChildAt(i);
				if (v instanceof ViewGroup) {
					changeFont((ViewGroup)v);
				}
				else if (v instanceof TextView) {
					changeFont(((TextView)v));
				}
			}
		} catch (Exception e) {
			LogUtils.e("font",e.getMessage());
		}
	}
	
	public static void changeTitleFonts(Activity activity,boolean isBold) {
		changeTitleFonts(activity,isBold ? typefaceBold : typefaceNormal);
	}
	
	public static CharSequence changeString(String message,boolean isBold) {
		if (!TextUtils.isEmpty(message)) {
			SpannableStringBuilder builder = SpannableStringBuilder.valueOf(message);
			builder.setSpan(new TypefaceSpan(isBold ? typefaceBold : typefaceNormal),0,
			                message.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			return builder;
		}
		return message;
	}
	
	public static CharSequence changeString(CharSequence message,boolean isBold) {
		if (!TextUtils.isEmpty(message)) {
			SpannableStringBuilder builder = SpannableStringBuilder.valueOf(message);
			builder.setSpan(new TypefaceSpan(isBold ? typefaceBold : typefaceNormal),0,
			                message.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			return builder;
		}
		return message;
	}
	
	
	/**
	 * 改变标题字体
	 *
	 * @param activity activity
	 * @param typeface 字体样式
	 */
	public static void changeTitleFonts(Activity activity,Typeface typeface) {
		try {
			if (activity instanceof AppCompatActivity) {
				android.support.v7.app.ActionBar actionBar = ((AppCompatActivity)activity)
						.getSupportActionBar();
				setTitle(actionBar,typeface,actionBar.getTitle().toString());
			}
			else {
				setTitle(activity.getActionBar(),typeface,
				         activity.getActionBar().getTitle().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置标题
	 *
	 * @param activity activity
	 * @param typeface 字体样式
	 * @param title 标题
	 */
	public static void setTitle(Activity activity,Typeface typeface,String title) {
		try {
			setTitle(activity.getActionBar(),typeface,title);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置标题
	 *
	 * @param actionBar 标题栏
	 * @param typeface 字体样式
	 * @param title 标题
	 */
	public static void setTitle(
			android.support.v7.app.ActionBar actionBar,Typeface typeface,String title)
	{
		try {
			if (typeface == null || actionBar == null) {
				return;
			}
			SpannableString sp = new SpannableString(title);
			sp.setSpan(new TypefaceSpan(typeface),0,sp.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			setTitle(actionBar,sp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置标题
	 *
	 * @param actionBar 标题栏
	 * @param typeface 字体
	 * @param title 标题内容
	 */
	public static void setTitle(ActionBar actionBar,Typeface typeface,String title) {
		try {
			if (typeface == null || actionBar == null) {
				return;
			}
			SpannableString sp = new SpannableString(title);
			sp.setSpan(new TypefaceSpan(typeface),0,sp.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			setTitle(actionBar,sp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置标题
	 *
	 * @param actionBar 标题栏
	 * @param spannableString 格式化后的标题
	 */
	public static void setTitle(
			android.support.v7.app.ActionBar actionBar,SpannableString spannableString)
	{
		try {
			if (Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN && Build.MANUFACTURER
					.toUpperCase().equals("LGE"))
			{
				actionBar.setTitle(spannableString.toString());
			}
			else {
				actionBar.setTitle(spannableString);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 设置标题
	 *
	 * @param actionBar 标题栏
	 * @param spannableString 格式化后的标题
	 */
	public static void setTitle(ActionBar actionBar,SpannableString spannableString) {
		try {
			if (Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN && Build.MANUFACTURER
					.toUpperCase().equals("LGE"))
			{
				actionBar.setTitle(spannableString.toString());
			}
			else {
				actionBar.setTitle(spannableString);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 字体样式
	 */
	private static class TypefaceSpan extends MetricAffectingSpan {
		
		Typeface typeface;
		
		TypefaceSpan(Typeface typeface) {
			this.typeface = typeface;
		}
		
		@Override
		public void updateMeasureState(TextPaint p) {
			p.setTypeface(typeface);
			p.setFlags(p.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
		}
		
		@Override
		public void updateDrawState(TextPaint tp) {
			tp.setTypeface(typeface);
			tp.setFlags(tp.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
		}
	}
	
	
	public static class DelegateLayoutInflaterFactory implements LayoutInflaterFactory {
		private AppCompatActivity mActivity;
		
		public DelegateLayoutInflaterFactory(AppCompatActivity activity) {
			mActivity = activity;
		}
		
		@Override
		public View onCreateView(View parent,String name,Context context,AttributeSet attrs) {
			AppCompatDelegate delegate = mActivity.getDelegate();
			View view = delegate.createView(parent,name,context,attrs);
			// 如果控件文字类型属于TextView，则加载第一种字体
			if (view instanceof TextView) {
				TextView textView = (TextView)view;
				textView.setIncludeFontPadding(false);
				//					android:includeFontPadding="false"
				if (ViewUtils.isBoldText(textView)) {
					textView.setTypeface(typefaceBold);
				}
				else {
					textView.setTypeface(typefaceNormal);
				}
			}
			else if (view instanceof ViewGroup) {
				view.getViewTreeObserver().addOnGlobalFocusChangeListener(
						new ViewTreeObserver.OnGlobalFocusChangeListener() {
							@Override
							public void onGlobalFocusChanged(View oldFocus,View newFocus) {
							
							}
						});
			}
			return view;
		}
	}
	
}
