package com.app.baselib;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.app.baselib.intface.IHandler;
import com.app.baselib.util.AppUtils;
import com.app.baselib.util.HandlerUtils;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

import org.litepal.LitePal;

/**
 * The type Base application.
 *
 * @项目名： MyComUtils
 * @包名： com.dgtle.baselib.base
 * @创建者: Noah.冯
 * @时间: 16 :43
 * @描述： Application基类
 */
public class BaseApplication extends Application {
	//static 代码段可以防止内存泄露
	static {
		//设置全局的Header构建器
		SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
			@Override
			public RefreshHeader createRefreshHeader(Context context,RefreshLayout layout) {
				//                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.black);//全局设置主题颜色
				return new MaterialHeader(
						context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
			}
		});
		//设置全局的Footer构建器
		SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
			@Override
			public RefreshFooter createRefreshFooter(Context context,RefreshLayout layout) {
				//指定为经典Footer，默认是 BallPulseFooter
				return new ClassicsFooter(context).setDrawableSize(20);
			}
		});
	}
	
	public MyHandler mHandler = new MyHandler();
	
//	public static void handleSSLHandshake() {
//		try {
//			TrustManager[] trustAllCerts = new TrustManager[]{
//					new X509TrustManager() {
//						public X509Certificate[] getAcceptedIssuers() {
//							return new X509Certificate[0];
//						}
//
//						@Override
//						public void checkClientTrusted(X509Certificate[] certs,String authType) {
//						}
//
//						@Override
//						public void checkServerTrusted(X509Certificate[] certs,String authType) {
//						}
//					}
//			};
//
//			SSLContext sc = SSLContext.getInstance("TLS");
//			// trustAllCerts信任所有的证书
//			sc.init(null,trustAllCerts,new SecureRandom());
//			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
//				@Override
//				public boolean verify(String hostname,SSLSession session) {
//					return true;
//				}
//			});
//		} catch (Exception ignored) {
//		}
//	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		AdapterUtils.initDensity(this);
		if (interceptOtherProcess()) {
			if (getPackageName().equals(AppUtils.getProcessName(this))) {
				initCreate();
			}
		}
		else {
			initCreate();
		}
	}
	
	/**
	 * Init create.初始化入口
	 */
	public void initCreate() {
		LitePal.initialize(this);
		HandlerUtils.initHandler(mHandler);
	}
	
	/**
	 * <p>是否拦截非包名进程初始化</p>
	 * <p>引入其他的第三方sdk，可能会造成多个以当前包名+：xxx 进程启动，造成application的onCreate方法多次启动</p>
	 *
	 * @return 默认为拦截 boolean
	 */
	public boolean interceptOtherProcess() {
		return true;
	}
	
	/**
	 * 程序终止时调用，可以手动调用，在此处释放某些内存引用。
	 */
	@Override
	public void onTerminate() {
		super.onTerminate();
	}
	
	public static class MyHandler extends Handler {
		public MyHandler() {
		}
		
		public MyHandler(Callback callback) {
			super(callback);
		}
		
		public MyHandler(Looper looper) {
			super(looper);
		}
		
		public MyHandler(Looper looper,Callback callback) {
			super(looper,callback);
		}
		
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			for (IHandler iHandler : HandlerUtils.getMessageList()) {
				if (iHandler.handleMessage(msg)) {
					return;
				}
			}
		}
	}
	
	//禁止字体随系统设置而变大
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		if (newConfig.fontScale != 1)//非默认值
			getResources();
		super.onConfigurationChanged(newConfig);
	}
	
	@Override
	public Resources getResources() {
		Resources res = super.getResources();
		if (res.getConfiguration().fontScale != 1) {//非默认值
			Configuration newConfig = new Configuration();
			newConfig.setToDefaults();//设置默认
			res.updateConfiguration(newConfig, res.getDisplayMetrics());
		}
		return res;
	}
}
