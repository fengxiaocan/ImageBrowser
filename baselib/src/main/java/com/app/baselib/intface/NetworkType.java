package com.app.baselib.intface;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * The interface Network type.
 *
 * @name： BaseApp
 * @package： com.dgtle.baselib.intface
 * @author: Noah.冯 QQ:1066537317
 * @time: 19 :30
 * @version: 1.1
 * @desc： TODO
 */
@IntDef({
        NetworkType.NETWORK_WIFI,
        NetworkType.NETWORK_4G,
        NetworkType.NETWORK_3G,
        NetworkType.NETWORK_2G,
        NetworkType.NETWORK_UNKNOWN,
        NetworkType.NETWORK_NO
})
@Retention(RetentionPolicy.SOURCE)
public @interface NetworkType {

    /**
     * The constant NETWORK_WIFI.
     */
    int NETWORK_WIFI = 0x01;
    /**
     * The constant NETWORK_4G.
     */
    int NETWORK_4G = 0x02;
    /**
     * The constant NETWORK_3G.
     */
    int NETWORK_3G = 0x03;
    /**
     * The constant NETWORK_2G.
     */
    int NETWORK_2G = 0x04;
    /**
     * The constant NETWORK_UNKNOWN.
     */
    int NETWORK_UNKNOWN = 0x05;
    /**
     * The constant NETWORK_NO.
     */
    int NETWORK_NO = 0x06;
}
