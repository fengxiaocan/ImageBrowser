package com.evil.imagebrowser.network;

import com.app.baselib.intface.NetStatuChangeListener;
import com.app.baselib.util.HandlerUtils;
import com.app.baselib.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import static com.app.base.intface.HandlerCode.Companion;
import static com.app.baselib.intface.INetStatu.NETWORK_MOBILE;
import static com.app.baselib.intface.INetStatu.NETWORK_WIFI;

/**
 * The type Net config.
 */
public class NetConfig implements NetStatuChangeListener {
    private static NetConfig sNetConfig;
    private List<NetStatuChangeListener> sNetStatuChangeListeners;
    private boolean isWifiNet;//是否是WiFi网络状态
    private boolean isMobileNet;//是否是4G网络状态

    private NetConfig() {
        isWifiNet = NetworkUtils.getNetWorkState() == NETWORK_WIFI;
        isMobileNet = NetworkUtils.getNetWorkState() == NETWORK_MOBILE;
        sNetStatuChangeListeners = new ArrayList<>();
        NetworkUtils.registerNetStatuChangeListener(this);
    }

    /**
     * Config net config.
     *
     * @return the net config
     */
    public static NetConfig config() {
        synchronized (NetConfig.class) {
            if (sNetConfig == null) {
                sNetConfig = new NetConfig();
            }
        }
        return sNetConfig;
    }

    /**
     * Is wifi net boolean.
     *
     * @return the boolean
     */
    public boolean isWifiNet() {
        return isWifiNet;
    }

    /**
     * Sets wifi net.
     *
     * @param wifiNet the wifi net
     */
    public void setWifiNet(boolean wifiNet) {
        isWifiNet = wifiNet;
    }

    /**
     * WiFi网络状态
     *
     * @param netChange
     */
    @Override
    public void onNetChange(int netChange) {
        checkListenerListIsNull();
        for (NetStatuChangeListener listener : sNetStatuChangeListeners) {
            listener.onNetChange(netChange);
        }
        isWifiNet = netChange == NETWORK_WIFI;
        isMobileNet = netChange == NETWORK_MOBILE;
        if (isWifiNet) {
            //网络不可用
            HandlerUtils.sendEmptyMessage(Companion.NET_WORK_CONNECT_WIFI);
            //            HandlerUtils.sendEmptyMessage(NET_WORK_CONNECT);
        } else if (isMobileNet) {
            HandlerUtils.sendEmptyMessage(Companion.NET_WORK_CONNECT_MOBILE);
            //            HandlerUtils.sendEmptyMessage(NET_WORK_CONNECT);
        } else {
            HandlerUtils.sendEmptyMessage(Companion.NET_WORK_ERROR);
        }
    }

    /**
     * Register net statu change listener.
     * 注册网络监听状态接口
     *
     * @param listener the listener
     */
    public void registerNetStatuChangeListener(
            NetStatuChangeListener listener
    )
    {
        checkListenerListIsNull();
        sNetStatuChangeListeners.add(listener);
    }

    /**
     * Register net statu change listener.
     *
     * @param listener the listener
     */
    public void unregisterNetStatuChangeListener(
            NetStatuChangeListener listener
    )
    {
        if (checkListenerListIsNull()) {
            sNetStatuChangeListeners.remove(listener);
        }
    }

    /**
     * Check list is null.
     */
    protected boolean checkListenerListIsNull() {
        if (sNetStatuChangeListeners == null) {
            sNetStatuChangeListeners = new ArrayList<>();
            return true;
        }
        return false;
    }
}
