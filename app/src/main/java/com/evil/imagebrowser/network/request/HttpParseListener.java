package com.evil.imagebrowser.network.request;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 14/6/18
 * @desc ...
 */
interface HttpParseListener {

    void onStart(long startTime);

    boolean disposeResponse(int responseCode);//返回false则不执行response(int responseCode,String respone)方法

    void onResponse(HttpURLConnection connection,HttpParams params) throws Exception;

    boolean readPriority();

    void onResponse(HttpParams params,InputStream respone)throws Exception;

    void replyResponse(HttpParams params,OutputStream outputStream)throws Exception;

    void onErrorResponse(HttpParams params,InputStream error)throws Exception;

    void onFailure(Throwable e);

    void onFinish(long elapsedTime);
}
