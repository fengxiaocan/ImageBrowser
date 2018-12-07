package com.app.baselib.intface;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.app.baselib.intface.INetStatu.NETWORK_MOBILE;
import static com.app.baselib.intface.INetStatu.NETWORK_NONE;
import static com.app.baselib.intface.INetStatu.NETWORK_WIFI;

/**
 * The interface Net statu.
 *
 * @name： BaseApp
 * @package： com.dgtle.baselib.intface
 * @author: Noah.冯 QQ:1066537317
 * @time: 19 :28
 * @version: 1.1
 * @desc： 网络状态
 */
@IntDef({INetStatu.NETWORK_NONE, INetStatu.NETWORK_MOBILE, INetStatu.NETWORK_WIFI})
@Retention(RetentionPolicy.SOURCE)
public @interface INetStatu{

    /**
     * 没有连接网络
     */
    int NETWORK_NONE   = -1;
    /**
     * 移动网络
     */
    int NETWORK_MOBILE = 0;
    /**
     * 无线网络
     */
    int NETWORK_WIFI   = 1;
}
