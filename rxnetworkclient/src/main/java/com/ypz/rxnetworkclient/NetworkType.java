package com.ypz.rxnetworkclient;

/**
 * Created by 易庞宙 on 2019 2019/2/22 16:20
 * email: 1986545332@qq.com
 */
public enum NetworkType {

    /**
     * Indicates this network uses a Cellular transport.
     * 手机移动网络
     */
     TRANSPORT_CELLULAR,

    /**
     * Indicates this network uses a Wi-Fi transport.
     * 手机wifi网络
     */
     TRANSPORT_WIFI,

    /**
     * Indicates this network uses a Bluetooth transport.
     * 手机蓝牙网络
     */
     TRANSPORT_BLUETOOTH,

    /**
     * Indicates this network uses an Ethernet transport.
     */
     TRANSPORT_ETHERNET ,

    /**
     * Indicates this network uses a VPN transport.
     * 手机vpn网络
     */
     TRANSPORT_VPN ,

    /**
     * Indicates this network uses a Wi-Fi Aware transport.
     * 手机wifi数据传输
     */
     TRANSPORT_WIFI_AWARE,

    /**
     * the network was unconnect
     * 手机网络没有连接或者断开网络
     * */
    TRANSPORT_UNCONNECT
}
