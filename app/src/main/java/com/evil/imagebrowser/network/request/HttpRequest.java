package com.evil.imagebrowser.network.request;

import java.net.HttpURLConnection;

import static com.dgtle.network.request.HttpHelper.Failure;
import static com.dgtle.network.request.HttpHelper.Finish;


/**
 * @创建者: Noah.冯
 * @时间: 19:18 @描述： TODO
 */
public final class HttpRequest {
    private HttpRequest() {
    }

    public void downFile(HttpParams params,DownFileCallback downFileCallback){

    }

    static void request(HttpParams params,HttpParseListener callback) {
        long startTime = System.currentTimeMillis();
        callback.onStart(startTime);
        HttpURLConnection conn = null;
        try {
            conn = params.disposeURL();
            IHttpIntercept intercept = params.getHttpIntercept();
            if (intercept == null|| !intercept.interceptConnection(conn)){
                conn.connect();
                if (params.isHasFile()) {
                    HttpHelper.updateFile(conn,params.getFiles());
                }
                HttpHelper.response(conn,params,callback);
            }
        } catch (Throwable e) {
            Failure(callback,e);
        } finally {
            Finish(conn,callback,startTime);
        }
    }
}
