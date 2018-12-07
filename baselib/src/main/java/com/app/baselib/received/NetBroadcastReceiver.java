package com.app.baselib.received;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.app.baselib.intface.NetStatuChangeListener;
import com.app.baselib.util.NetworkUtils;

import static com.app.baselib.util.NetworkUtils.getNetStatuChangeListener;


/**
 * @name： BaseApp
 * @package： com.dgtle.baselib.base.received
 * @author: Noah.冯 QQ:1066537317
 * @time: 19:23
 * @version: 1.1
 * @desc： 网络状态变化监听器
 */

public class NetBroadcastReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context,Intent intent){
        // 如果相等的话就说明网络状态发生了变化
        if(intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)){
            NetStatuChangeListener netStatuChangeListener =
                    getNetStatuChangeListener();
            if(netStatuChangeListener != null){
                int netWorkState = NetworkUtils.getNetWorkState();
                // 接口回调传过去状态的类型
                netStatuChangeListener.onNetChange(netWorkState);
            }
        }
    }
}
