package com.evil.imagebrowser.network;

import com.app.base.Api;
import com.app.baselib.gson.GsonUtils;
import com.app.baselib.util.Utils;
import com.dgtle.network.cookie.CookiesManager;
import com.dgtle.network.cookie.PersistentCookieStore;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofigUtils {
	private static CookiesManager sCookiesManager;
	
	static {
		sCookiesManager = new CookiesManager(new PersistentCookieStore(Utils.getContext()));
	}
	
	public static CookiesManager getCookieStore() {
		if (sCookiesManager == null) {
			sCookiesManager = new CookiesManager(new PersistentCookieStore(Utils.getContext()));
		}
		return sCookiesManager;
	}
	
	public static <T> T create(Class<T> service) {
		OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
		builder.readTimeout(10,TimeUnit.SECONDS);
		builder.connectTimeout(9,TimeUnit.SECONDS);
		builder.cookieJar(getCookieStore());
		if (Utils.IsAppDebug()) {
			builder.addInterceptor(new HttpLogInterceptor(HttpLogInterceptor.Level.BODY));
		}
		Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.Companion.BASE_URL)
		                                          .client(builder.build()).addConverterFactory(
						GsonConverterFactory.create(GsonUtils.getGson())).build();
		return retrofit.create(service);
	}
	
	public static <T> T create(Class<T> service,String baseUrl) {
		OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
		builder.readTimeout(10,TimeUnit.SECONDS);
		builder.connectTimeout(9,TimeUnit.SECONDS);
		builder.cookieJar(getCookieStore());
		if (Utils.IsAppDebug()) {
			builder.addInterceptor(new HttpLogInterceptor(HttpLogInterceptor.Level.BODY));
		}
		return new Retrofit.Builder().baseUrl(baseUrl).client(builder.build()).addConverterFactory(
				GsonConverterFactory.create(GsonUtils.getGson())).build().create(service);
	}
	
}
