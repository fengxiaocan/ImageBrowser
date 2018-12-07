package com.evil.imagebrowser.network.cookie.interceptor;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * 保存cookies时候的拦截器
 * Created by dujigui on 7/14/16.
 */
public interface CookieInterceptor {
    List<Cookie> intercept(HttpUrl httpUrl,List<Cookie> cookies);
}
