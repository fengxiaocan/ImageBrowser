package com.evil.imagebrowser.network.cookie;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

public class PersistentCookieStore {
    private static final String COOKIE_PATH = "xcxcookies";
    private HashMap<String,CookieInfo> cookieLists;
    private Context mContext;
    private File parentPath;
    private String jdmshash;
    private List<MatchStrategy> matchStrategies; //过滤规则添加

    public PersistentCookieStore(Context context) {
        mContext = context;
        cookieLists = new HashMap();
        matchStrategies = new ArrayList<>();
        File filesDir = mContext.getFilesDir();
        parentPath = new File(filesDir,COOKIE_PATH);
        if (!parentPath.exists()) {
            parentPath.mkdirs();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                //将持久化的cookies缓存到内存中 即map cookies
                //                ArrayList<File> files = CookiesUtils.listFile(parentPath);
                List<CookieInfo> list = CookiesSqlHelper.getHelper().queryAll();
                for (CookieInfo info : list) {
                    cookieLists.put(info.getCookieKey(),info);
                }
                //                for (File file : files) {
                //                    CookieInfo info = CookiesUtils.get(file);
                //                  cookieLists.put(info.getCookieKey(),info);
                //                }
            }
        }).start();
    }

    public void add(HttpUrl url,Cookie cookie) {
        if (cookie.name().contains("wnqf_2132_jdmhash")) {
            if (!"deleted".equals(cookie.value())) {
                jdmshash = cookie.value();
            }
        }
        CookieInfo cookieInfo = new CookieInfo(cookie);
        String key = cookieInfo.getCookieKey();
        if (cookieLists.containsKey(key)) {
            //包含该cookie,更新
            if (!cookieLists.get(key).equals(cookieInfo)) {
                //不相等，更新
                cookieLists.remove(key);
                cookieLists.put(key,cookieInfo);
                CookiesSqlHelper.getHelper().updateSingle(cookieInfo);
                //                CookiesUtils.save(new File(parentPath,cookieInfo.getFileName()),cookieInfo);
            }
        } else {
            cookieLists.put(key,cookieInfo);
            CookiesSqlHelper.getHelper().insertSingle(cookieInfo);
            //            CookiesUtils.save(new File(parentPath,cookieInfo.getFileName()),cookieInfo);
        }
        //将cookies缓存到内存中 如果缓存过期 就重置此cookie
        //讲cookies持久化到本地
    }

    public List<Cookie> get(HttpUrl url) {
        ArrayList<Cookie> ret = new ArrayList<>();
        for (String key : cookieLists.keySet()) {
            CookieInfo info = cookieLists.get(key);
            if (info.matches(url)) {
                if (info.getName().contains("wnqf_2132_jdmhash")) {
                    if ("deleted".equals(info.getValue()) && jdmshash != null) {
                        info.setValue(jdmshash);
                    }
                }
                ret.add(info.toCookie());
            } else {
                for (MatchStrategy matchStrategy : matchStrategies) {
                    Cookie cookie = info.toCookie();
                    if (matchStrategy.match(url,cookie)) {
                        ret.add(cookie);
                        break;
                    }
                }
            }
        }
        return ret;
    }

    public void clear() {
        cookieLists.clear();
        CookiesSqlHelper.getHelper().clear();
        //        CookiesUtils.deleteDir(parentPath);
    }

    public boolean remove(HttpUrl url,Cookie cookie) {
        CookiesSqlHelper.getHelper().deleteSingle(CookieInfo.createCookieKey(cookie));
        return cookieLists.remove(CookieInfo.createCookieKey(cookie)) != null;
    }

    public List<Cookie> getCookies() {
        ArrayList<Cookie> ret = new ArrayList<>();
        for (String key : cookieLists.keySet()) {
            CookieInfo info = cookieLists.get(key);
            ret.add(info.toCookie());
        }
        return ret;
    }

    /**
     * 添加过滤规则
     *
     * @param matchStrategy
     */
    public void addStrategy(MatchStrategy matchStrategy) {
        matchStrategies.add(matchStrategy);
    }
}