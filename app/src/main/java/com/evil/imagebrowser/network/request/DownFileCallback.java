package com.evil.imagebrowser.network.request;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 14/6/18
 * @desc ...
 */
public class DownFileCallback {

    public void onStart(long startTime){}

    public void onResponseLength(int length){}

    public void onResponseMessage(String msg){}

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onResponseLength(long length){}

    public void onProgress(long total,long progress){}

    public void onResponse(HttpParams params,InputStream respone){}

    public void replyResponse(HttpParams params,OutputStream outputStream){}

    public void onErrorResponse(String error){}

    public void onFailure(Throwable e){}

    public void onFinish(long elapsedTime){}
}
