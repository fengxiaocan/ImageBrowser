package com.evil.imagebrowser.network.cookie.interceptor;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

import static okhttp3.internal.http.HttpDate.MAX_DATE;

public class LoadCookieInterceptor implements CookieInterceptor {
    @Override
    public List<Cookie> intercept(HttpUrl url, List<Cookie> cookies) {
        loadAuth(url, cookies);
        return cookies;
    }

    private void loadAuth(HttpUrl url, List<Cookie> result) {
        if (result == null){
            return;
        }
        Cookie cookieDelete = null;
        Cookie cookieDelete1 = null;
        boolean authfind = false;
        boolean saltfind = false;

        for (int i = 0; i < result.size(); i++) {
            Cookie cookie = result.get(i);
            if (cookie.name().contains("auth")) {
                cookieDelete = cookie;
                authfind = true;
            }
            else if (cookie.name().contains("saltkey")) {
                cookieDelete1 = cookie;
                saltfind = true;
            }

            if(authfind && saltfind) break;
        }

        if (cookieDelete != null) {
            Cookie.Builder builder = new Cookie.Builder();
            builder.domain(cookieDelete.domain());
            builder.path(cookieDelete.path());
            builder.name(cookieDelete.name());
            builder.value(cookieDelete.value());
            builder.expiresAt(MAX_DATE);
            if(cookieDelete.secure()) builder.secure();
            if(cookieDelete.hostOnly()) builder.hostOnlyDomain(cookieDelete.domain());
            if(cookieDelete.httpOnly()) builder.httpOnly();

            result.remove(cookieDelete);

            result.add(builder.build());
        }

        if (cookieDelete1 != null) {
            Cookie.Builder builder = new Cookie.Builder();
            builder.domain(cookieDelete1.domain());
            builder.path(cookieDelete1.path());
            builder.name(cookieDelete1.name());
            builder.value(cookieDelete1.value());
            builder.expiresAt(MAX_DATE);
            if(cookieDelete1.secure()) builder.secure();
            if(cookieDelete1.hostOnly()) builder.hostOnlyDomain(cookieDelete1.domain());
            if(cookieDelete1.httpOnly()) builder.httpOnly();

            result.remove(cookieDelete1);

            result.add(builder.build());
        }
    }

}
