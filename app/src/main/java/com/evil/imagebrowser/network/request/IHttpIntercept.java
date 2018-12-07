package com.evil.imagebrowser.network.request;

import java.net.HttpURLConnection;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 14/6/18
 * @desc ...
 */
public interface IHttpIntercept {
    boolean interceptConnection(HttpURLConnection connection) throws Exception;
}
