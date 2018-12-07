package com.evil.imagebrowser.network.request;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 14/6/18
 * @desc http 参数
 */
public final class HttpParams {
    private String url;//请求地址
    private @HttpMethod.TYPE
    String method;//请求方法
    private int connectTimeout;
    private int readTimeout;
    private boolean usedCaches;
    private boolean doOutput;
    private boolean doInput;
    private LinkedHashMap<String,String> headers;
    private LinkedHashMap<String,String> params;
    private LinkedHashMap<String,String> suffixs;
    private LinkedHashMap<String,String> files;
    private boolean isHasFile;
    private String charsetName;
    private IHttpIntercept mHttpIntercept;
    private String downFilePath;

    private HttpParams(Builder builder) {
        url = builder.urlBuffer.toString();
        method = builder.method;
        connectTimeout = builder.connectTimeout;
        readTimeout = builder.readTimeout;
        usedCaches = builder.usedCaches;
        doOutput = builder.doOutput;
        doInput = builder.doInput;
        headers = builder.headers;
        downFilePath = builder.downFile;
        params = builder.params;
        charsetName = builder.charsetName;
        files = builder.fileHashMap;
        suffixs = builder.suffixHashMap;
        isHasFile = builder.isHasFile;
        mHttpIntercept = builder.mHttpIntercept;
    }

    public static Builder url(String url) {
        return new Builder(url);
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public long getConnectTimeout() {
        return connectTimeout;
    }

    public IHttpIntercept getHttpIntercept() {
        return mHttpIntercept;
    }

    public long getReadTimeout() {
        return readTimeout;
    }

    public boolean isUsedCaches() {
        return usedCaches;
    }

    public boolean isDoOutput() {
        return doOutput;
    }

    public boolean isDoInput() {
        return doInput;
    }

    public LinkedHashMap<String,String> getHeaders() {
        return headers;
    }

    void setHeaders(HttpURLConnection httpURL) {
        if (headers == null || headers.size() == 0) {
            return;
        }
        for (String key : headers.keySet()) {
            httpURL.setRequestProperty(key,headers.get(key));
        }
    }

    public boolean hasHeaders() {
        return headers == null;
    }

    public boolean hasParams() {
        return params == null;
    }

    public String getDownFilePath() {
        return downFilePath;
    }

    public LinkedHashMap<String,String> getParams() {
        return params;
    }

    public LinkedHashMap<String,String> getSuffixs() {
        return suffixs;
    }

    public LinkedHashMap<String,String> getFiles() {
        return files;
    }

    public boolean isHasFile() {
        return isHasFile;
    }

    public String getCharsetName() {
        return charsetName;
    }

    HttpURLConnection disposeURL() throws IOException {
        boolean isPost = method.equals(HttpMethod.POST);
        StringBuffer buffer = new StringBuffer(url);
        buffer.append("?");
        if (suffixs != null && suffixs.size() > 0) {
            for (String key : suffixs.keySet()) {
                buffer.append(key);
                buffer.append("=");
                String value = suffixs.get(key);
                buffer.append(value);
                buffer.append("&");
            }
        }
        if (!isPost) {
            if (params != null && params.size() > 0) {
                for (String key : params.keySet()) {
                    buffer.append(key);
                    buffer.append("=");
                    String value = params.get(key);
                    buffer.append(value);
                    buffer.append("&");
                }
            }
        }
        buffer.delete(buffer.length() - 1,buffer.length());

        HttpHelper.LOGD(buffer.toString());

        URL url = new URL(buffer.toString());

        HttpURLConnection httpUrlConn = (HttpURLConnection)url.openConnection();

        if (headers != null) {
            for (String key : headers.keySet()) {
                httpUrlConn.setRequestProperty(key,headers.get(key));
            }
        }

        if (isPost && params != null) {
            for (String key : params.keySet()) {
                httpUrlConn.setRequestProperty(key,params.get(key));
            }
        }
        httpUrlConn.setConnectTimeout(connectTimeout);
        httpUrlConn.setReadTimeout(readTimeout);
        httpUrlConn.setUseCaches(usedCaches);
        httpUrlConn.setDoOutput(doOutput);
        httpUrlConn.setDoInput(doInput);
        return httpUrlConn;
    }

    public static final class Builder {
        private StringBuffer urlBuffer;
        private @HttpMethod.TYPE
        String method = HttpMethod.GET;
        private int connectTimeout = HttpConfig.getConfig().getConnectTimeout();
        private int readTimeout = HttpConfig.getConfig().getReadTimeout();
        private boolean usedCaches = HttpConfig.getConfig().isUseCaches();
        private boolean doOutput = HttpConfig.getConfig().isDooutput();
        private boolean doInput = HttpConfig.getConfig().isDoinput();
        private LinkedHashMap<String,String> headers = HttpConfig.getConfig().getHeaders();
        private LinkedHashMap<String,String> params = HttpConfig.getConfig().getParams();
        private LinkedHashMap<String,String> suffixHashMap;
        private LinkedHashMap<String,String> fileHashMap;
        private boolean isHasFile = false;
        private String charsetName = HttpCharset.UTF_8;
        private IHttpIntercept mHttpIntercept;
        private String downFile;

        private Builder(String url) {
            urlBuffer = new StringBuffer(url);
        }

        public Builder baseUrl(String val) {
            urlBuffer.insert(0,val);
            return this;
        }

        public Builder path(String val) {
            urlBuffer.append(val);
            return this;
        }

        public Builder charsetName(String val) {
            charsetName = val;
            return this;
        }

        public Builder method(@HttpMethod.TYPE String val) {
            method = val;
            return this;
        }

        public Builder connectTimeout(int val) {
            connectTimeout = val;
            return this;
        }

        public Builder readTimeout(int val) {
            readTimeout = val;
            return this;
        }

        public Builder usedCaches(boolean val) {
            usedCaches = val;
            return this;
        }

        public Builder doOutput(boolean val) {
            doOutput = val;
            return this;
        }

        public Builder doInput(boolean val) {
            doInput = val;
            return this;
        }

        public Builder addHeaders(String key,String value) {
            HttpHelper.checkHashMap(headers);
            headers.put(key,value);
            return this;
        }

        public Builder addParams(String key,String value) {
            HttpHelper.checkHashMap(params);
            params.put(key,value);
            return this;
        }

        public Builder addParams(String key,long value) {
            HttpHelper.checkHashMap(params);
            params.put(key,String.valueOf(value));
            return this;
        }

        public Builder addParams(String key,int value) {
            HttpHelper.checkHashMap(params);
            params.put(key,String.valueOf(value));
            return this;
        }

        public Builder addIntercept(IHttpIntercept iHttpIntercept) {
            mHttpIntercept = iHttpIntercept;
            return this;
        }

        public Builder addParams(String key,double value) {
            HttpHelper.checkHashMap(params);
            params.put(key,String.valueOf(value));
            return this;
        }

        public Builder addParams(String key,float value) {
            HttpHelper.checkHashMap(params);
            params.put(key,String.valueOf(value));
            return this;
        }

        public Builder addParams(String key,boolean value) {
            HttpHelper.checkHashMap(params);
            params.put(key,String.valueOf(value));
            return this;
        }

        public Builder addParams(String key,char value) {
            HttpHelper.checkHashMap(params);
            params.put(key,String.valueOf(value));
            return this;
        }

        public Builder addFile(String key,String value) {
            HttpHelper.checkHashMap(fileHashMap);
            fileHashMap.put(key,value);
            isHasFile = true;
            return this;
        }

        public Builder addDownFilePath(String downFile) {
            this.downFile = downFile;
            return this;
        }

        public Builder addSuffix(String key,String value) {
            HttpHelper.checkHashMap(suffixHashMap);
            suffixHashMap.put(key,value);
            return this;
        }

        public Builder addFile(String key,File value) {
            if (value != null && value.exists()) {
                addFile(key,value.getAbsolutePath());
            }
            return this;
        }

        public HttpParams build() {
            return new HttpParams(this);
        }
    }
}
