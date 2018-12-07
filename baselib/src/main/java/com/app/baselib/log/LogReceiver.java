package com.app.baselib.log;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static com.app.baselib.log.LogUtils.ACTION_CANCEL_LOG_BUTTON;
import static com.app.baselib.log.LogUtils.ACTION_CREATE_LOG_BUTTON;
import static com.app.baselib.log.LogUtils.ACTION_HIDE_LOG_BUTTON;
import static com.app.baselib.log.LogUtils.ACTION_SHOW_LOG_BUTTON;


/**
 * @name： BaseApp
 * @package： com.dgtle.baselib.base.received
 * @author: Noah.冯 QQ:1066537317
 * @time: 19:23
 * @version: 1.1
 * @desc： 日志的图标
 */

public class LogReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (ACTION_CREATE_LOG_BUTTON.equals(action)) {
            LogUtils.createLogButton(context);
        } else if (ACTION_SHOW_LOG_BUTTON.equals(action)) {
            LogUtils.showLogIcon(context);
        } else if (ACTION_HIDE_LOG_BUTTON.equals(action)) {
            LogUtils.hideLogIcon();
        } else if (ACTION_CANCEL_LOG_BUTTON.equals(action)) {
            LogUtils.cancelLogIcon();
        }
    }
}
