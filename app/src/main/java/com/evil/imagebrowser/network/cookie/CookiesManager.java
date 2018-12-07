package com.evil.imagebrowser.network.cookie;



import com.evil.imagebrowser.network.cookie.interceptor.CookieInterceptor;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class CookiesManager implements CookieJar {
    private CookieInterceptor saveInterceptor;
    private CookieInterceptor loadInterceptor;
    private PersistentCookieStore cookieStore;

    public CookiesManager(PersistentCookieStore cookieStore) {
        this.cookieStore = cookieStore;
    }

    public PersistentCookieStore getCookieStore() {
        return cookieStore;
    }

    public CookieInterceptor getSaveInterceptor() {
        return saveInterceptor;
    }

    public void setSaveInterceptor(CookieInterceptor saveInterceptor) {
        this.saveInterceptor = saveInterceptor;
    }

    public CookieInterceptor getLoadInterceptor() {
        return loadInterceptor;
    }

    public void setLoadInterceptor(CookieInterceptor loadInterceptor) {
        this.loadInterceptor = loadInterceptor;
    }

    @Override
    public void saveFromResponse(HttpUrl url,List<Cookie> cookies) {
        if (saveInterceptor != null) {
            cookies = saveInterceptor.intercept(url,cookies);
        }
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url,item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        if (loadInterceptor != null) {
            cookies = loadInterceptor.intercept(url,cookies);
        }
        return cookies;
    }

    public void clear() {
        cookieStore.clear();
    }
}
