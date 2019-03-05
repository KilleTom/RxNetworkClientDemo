package com.ypz.rxnetworkclientdemo;

import android.app.Application;

import com.ypz.rxnetworkclient.RxNetworkClient;

/**
 * Created by 易庞宙 on 2019 2019/2/23 00:21
 * email: 1986545332@qq.com
 */
public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RxNetworkClient.Companion.getNetworkClient().initAppliaction(this);
    }
}
