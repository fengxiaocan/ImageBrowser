package com.evil.imagebrowser.network.request;

interface HttpRequestCallback {
    void onStart(long startTime);

    boolean disposeResponse(int responseCode);//返回false则不执行response(int responseCode,String respone)方法

    void onResponse(int responseCode,String respone);

    void onErrorResponse(String error);

    void onFailure(Exception e);

    void onFinish(long elapsedTime);
}
