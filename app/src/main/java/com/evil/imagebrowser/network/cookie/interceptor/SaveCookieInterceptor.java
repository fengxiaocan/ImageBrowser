package com.evil.imagebrowser.network.cookie.interceptor;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

import static okhttp3.internal.http.HttpDate.MAX_DATE;

public class SaveCookieInterceptor implements CookieInterceptor {
    @Override
    public List<Cookie> intercept(HttpUrl url,List<Cookie> cookies) {
        if (cookies == null) {
            return new ArrayList<>();
        }
        changeMobile(url,cookies);
        saveSecPhone(url,cookies);
        saveSecEmail(url,cookies);
        saveJdmsHash(url,cookies);
        return cookies;
    }


    private void saveJdmsHash(HttpUrl url,List<Cookie> result) {
        for (int i = 0;i < result.size();i++) {
            Cookie cookie = result.get(i);
            if (cookie.name().contains("jdmhash")) {
                if (cookie.value().contains("deleted")) {
                    result.remove(i);
                    result.add(cookie);
                }
            }
        }
    }

    private void changeMobile(HttpUrl url,List<Cookie> result) {
        for (int i = 0;i < result.size();i++) {
            Cookie cookie = result.get(i);
            if (cookie.name().contains("mobile")) {
                Cookie cookie1 = Cookie.parse(url,
                                              cookie.toString().replace("mobile=no","mobile=yes")
                );
                result.set(i,cookie1);
            }
        }
    }

    private void saveSecPhone(HttpUrl url,List<Cookie> result) {
        Cookie cookieDelete = null;
        Cookie cookieTarget = null;
        for (int i = 0;i < result.size();i++) {
            Cookie cookie = result.get(i);
            if (cookie.name().contains("jdmsecphone")) {
                if (cookie.value().contains("deleted")) {
                    cookieDelete = cookie;
                } else {
                    cookieTarget = cookie;
                }
            }
        }

        if (cookieTarget != null) {
            Cookie build = new Cookie.Builder().hostOnlyDomain(url.host())
                                               .path("/")
                                               .name(cookieTarget.name())
                                               .value(cookieTarget.value())
                                               .expiresAt(MAX_DATE)
                                               .build();
            result.remove(cookieTarget);
            if (cookieDelete != null) {
                result.remove(cookieDelete);
            }
            result.add(build);
        }
    }

    private void saveSecEmail(HttpUrl url,List<Cookie> result) {
        //        wnqf_2132_jdmsecmail	6a37bc782d3ab41189
        Cookie cookieDelete = null;
        Cookie cookieTarget = null;
        for (int i = 0;i < result.size();i++) {
            Cookie cookie = result.get(i);
            if (cookie.name().contains("jdmsecmail")) {
                if (cookie.value().contains("deleted")) {
                    cookieDelete = cookie;
                } else {
                    cookieTarget = cookie;
                }
            }
        }

        if (cookieTarget != null) {
            Cookie build = new Cookie.Builder().hostOnlyDomain(url.host())
                                               .path("/")
                                               .name(cookieTarget.name())
                                               .value(cookieTarget.value())
                                               .expiresAt(MAX_DATE)
                                               .build();
            result.remove(cookieTarget);
            if (cookieDelete != null) {
                result.remove(cookieDelete);
            }
            result.add(build);
        }
    }
}
