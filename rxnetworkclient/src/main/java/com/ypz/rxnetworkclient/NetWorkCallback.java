package com.ypz.rxnetworkclient;

import android.annotation.SuppressLint;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.util.Log;

/**
 * Created by 易庞宙 on 2019 2019/2/22 14:40
 * email: 1986545332@qq.com
 */
class NetWorkCallback extends ConnectivityManager.NetworkCallback {

    private final String tag = "RxNetworkClient-NetworkCallback";

    /**
     * 网络可用的回调
     */
    @SuppressLint("LongLogTag")
    @Override
    public void onAvailable(Network network) {
        super.onAvailable(network);
        Log.d(tag, "network is connect");
    }

    /**
     * 网络丢失的回调
     */
    @SuppressLint("LongLogTag")
    @Override
    public void onLost(Network network) {
        super.onLost(network);

        RxNetWorkBus.getNetWorkBus().onPost(NetworkType.TRANSPORT_UNCONNECT);
        Log.d(tag, "network is unconnect");
    }

    /**
     * 网络状态发生改变的回调
     */
    @SuppressLint("LongLogTag")
    @Override
    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        Log.d(tag, "network status is change");
        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                RxNetWorkBus.getNetWorkBus().onPost(NetworkType.TRANSPORT_CELLULAR);
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                RxNetWorkBus.getNetWorkBus().onPost(NetworkType.TRANSPORT_WIFI);
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)) {
                RxNetWorkBus.getNetWorkBus().onPost(NetworkType.TRANSPORT_BLUETOOTH);
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                RxNetWorkBus.getNetWorkBus().onPost(NetworkType.TRANSPORT_ETHERNET);
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                RxNetWorkBus.getNetWorkBus().onPost(NetworkType.TRANSPORT_VPN);
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE)) {
                RxNetWorkBus.getNetWorkBus().onPost(NetworkType.TRANSPORT_WIFI_AWARE);
            }
        }
    }


    /**
     * 无可用网络链接回调
     */
    @SuppressLint("LongLogTag")
    @Override
    public void onUnavailable() {
        RxNetWorkBus.getNetWorkBus().onPost(NetworkType.TRANSPORT_UNCONNECT);
        Log.d(tag, "network is unconnect");
    }
}
