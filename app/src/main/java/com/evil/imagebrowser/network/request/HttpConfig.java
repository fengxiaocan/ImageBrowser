package com.evil.imagebrowser.network.request;

import java.util.LinkedHashMap;

import static com.dgtle.network.request.HttpHelper.checkHashMap;
import static com.dgtle.network.request.HttpSimulator.ANDROID_WEB_SIMULATOR;


/**
 * @项目名： SuperHttp
 * @包名： com.evil.http
 * @创建者: Noah.冯
 * @时间: 9:37
 * @描述： TODO
 */

public final class HttpConfig {
    public final static String ContentType = "Content-Type";
    public static final String ContentTypeDefault = "application/x-www-form-urlencoded";
    //最大读取超时时间
    public static final int MAX_READ_TIMEOUT = 5000;
    //最大连接超时时间
    public static final int MAX_CONNECT_TIMEOUT = 5000;

    private static HttpConfig sHttpConfig;
    //链接超时时间
    private int mConnectTimeout = MAX_CONNECT_TIMEOUT;
    //读取超时时间
    private int mReadTimeout = MAX_READ_TIMEOUT;
    //是否使用缓存
    private boolean useCaches = false;
    //是否要获取连接输入流
    private boolean doinput = true;
    //是否要获取连接输出流
    private boolean dooutput = true;
    //是否打开日志开关
    private boolean openLog = true;
    //日志标记
    private String  logTag = "com.evil.superhttp";
    //上传文件的分界线
    private String  boundary = "--------------superhttp------------";

    //请求模拟器标识
    private String userAgent = ANDROID_WEB_SIMULATOR;

    private LinkedHashMap<String,String> mHeaderMap;
    private LinkedHashMap<String,String> mParamsMap;

    private HttpConfig(Builder builder) {
        setConnectTimeout(builder.mConnectTimeout);
        setReadTimeout(builder.mReadTimeout);
        setUseCaches(builder.useCaches);
        setDoinput(builder.doinput);
        setDooutput(builder.dooutput);
        openLog = builder.openLog;
        userAgent = builder.userAgent;
        boundary = builder.boundary;
        logTag = builder.logTag;
        mHeaderMap = builder.mParamsMap;
    }

    private HttpConfig() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static synchronized HttpConfig getConfig() {
        if (sHttpConfig == null) {
            sHttpConfig = HttpConfig.builder()
                                    .addHeader("Connection","Keep-Alive")
                                    .addHeader(ContentType,ContentTypeDefault)
                                    .addParams("User-Agent",ANDROID_WEB_SIMULATOR)
                                    .build();
        }
        return sHttpConfig;
    }

    public boolean isOpenLog() {
        return openLog;
    }

    public String getBoundary() {
        return boundary;
    }

    public void setBoundary(String boundary) {
        this.boundary = boundary;
    }

    public void setOpenLog(boolean openLog) {
        this.openLog = openLog;
    }

    public String getLogTag() {
        return logTag;
    }

    public void setLogTag(String logTag) {
        this.logTag = logTag;
    }

    public static void setHttpConfig(HttpConfig config) {
        sHttpConfig = config;
    }

    public int getConnectTimeout() {
        return mConnectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        mConnectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return mReadTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        mReadTimeout = readTimeout;
    }

    public boolean isUseCaches() {
        return useCaches;
    }

    public void setUseCaches(boolean useCaches) {
        this.useCaches = useCaches;
    }

    public boolean isDoinput() {
        return doinput;
    }

    public void setDoinput(boolean doinput) {
        this.doinput = doinput;
    }

    public boolean isDooutput() {
        return dooutput;
    }

    public void setDooutput(boolean dooutput) {
        this.dooutput = dooutput;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void addHeader(String key,String header) {
        checkHashMap(mHeaderMap);
        mHeaderMap.put(key,header);
    }

    public void addParams(String key,String header) {
        checkHashMap(mParamsMap);
        mParamsMap.put(key,header);
    }

    public LinkedHashMap<String,String> getHeaders() {
        return mHeaderMap;
    }

    public LinkedHashMap<String,String> getParams() {
        return mParamsMap;
    }

    public static final class Builder {
        private int mConnectTimeout;
        private int mReadTimeout;
        private boolean useCaches;
        private boolean doinput;
        private boolean dooutput;
        private boolean openLog=true;
        private String userAgent;
        private String boundary = "--------------superhttp------------";
        private String logTag;
        private LinkedHashMap<String,String> mHeaderMap;
        private LinkedHashMap<String,String> mParamsMap;

        private Builder() {
        }

        public Builder connectTimeout(int val) {
            mConnectTimeout = val;
            return this;
        }

        public Builder readTimeout(int val) {
            mReadTimeout = val;
            return this;
        }

        public Builder useCaches(boolean val) {
            useCaches = val;
            return this;
        }

        public Builder doinput(boolean val) {
            doinput = val;
            return this;
        }

        public Builder dooutput(boolean val) {
            dooutput = val;
            return this;
        }

        public Builder userAgent(String val) {
            userAgent = val;
            return this;
        }

        public Builder boundary(String val) {
            boundary = val;
            return this;
        }

        public Builder logTag(String val) {
            logTag = val;
            return this;
        }

        public Builder addHeader(String key,String value) {
            checkHashMap(mHeaderMap);
            mHeaderMap.put(key,value);
            return this;
        }

        public Builder addParams(String key,String value) {
            checkHashMap(mParamsMap);
            mParamsMap.put(key,value);
            return this;
        }

        public HttpConfig build() {
            return new HttpConfig(this);
        }
    }
}
