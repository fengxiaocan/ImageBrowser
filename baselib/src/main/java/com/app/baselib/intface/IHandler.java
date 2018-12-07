package com.app.baselib.intface;

import android.os.Message;

/**
 * @name： BaseApp
 * @package： com.dgtle.baselib.intface
 * @author: Noah.冯 QQ:1066537317
 * @time: 16:36
 * @version: 1.1
 * @desc： handler接口
 */

public interface IHandler{
    boolean handleMessage(Message msg);
}
