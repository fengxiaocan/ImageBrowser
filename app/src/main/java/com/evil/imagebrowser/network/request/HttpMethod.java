package com.evil.imagebrowser.network.request;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @项目名： SuperHttp
 * @包名： com.evil.http
 * @创建者: Noah.冯
 * @时间: 11:34
 * @描述： TODO
 */

public interface HttpMethod{
    String GET = "GET";
    String POST = "POST";
    String HEAD = "HEAD";
    String OPTIONS = "OPTIONS";
    String PUT = "PUT";
    String DELETE = "DELETE";
    String TRACE = "TRACE";
    @StringDef({GET,POST,HEAD,OPTIONS,PUT,DELETE,TRACE})
    @Retention(RetentionPolicy.SOURCE)
    @interface TYPE{}
}
