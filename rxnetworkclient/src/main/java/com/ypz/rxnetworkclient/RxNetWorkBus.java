package com.ypz.rxnetworkclient;

import java.util.HashMap;

import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * Created by 易庞宙 on 2019 2019/2/22 15:13
 * email: 1986545332@qq.com
 */
class RxNetWorkBus {

    private static RxNetWorkBus netWorkBus;

    private final FlowableProcessor<Object> flowableBus;

    private HashMap<Object, DisposableObserver> networkObserver;


    private RxNetWorkBus() {
        flowableBus = PublishProcessor.create().toSerialized();
        networkObserver = new HashMap<>();
    }

    public static RxNetWorkBus getNetWorkBus() {
        if (netWorkBus == null) {
            synchronized (RxNetWorkBus.class) {
                netWorkBus = new RxNetWorkBus();
            }
        }
        return netWorkBus;
    }

    public void onPost(NetworkType networkType) {
        flowableBus.onNext(networkType);
    }

    public FlowableProcessor<Object> getFlowableBus() {
        return flowableBus;
    }

    protected void registerNetworkStatus(Object tag, DisposableObserver<NetworkType> observer) {
        if (!networkObserver.isEmpty()) {
            DisposableObserver disposableObserver = networkObserver.get(tag);
            if (disposableObserver != null) {
                disposableObserver.dispose();
                networkObserver.remove(tag);
                networkObserver.put(tag, observer);
            }
        } else {
            networkObserver.put(tag, observer);
        }
        flowableBus.toObservable().map(new Function<Object, NetworkType>() {
            @Override
            public NetworkType apply(Object o) throws Exception {
                return (NetworkType) o;
            }
        }).subscribe(observer);
    }

    protected void unregisterNetworkStatus(Object tag) {
        if (!networkObserver.isEmpty()) {
            DisposableObserver disposableObserver = networkObserver.get(tag);
            if (disposableObserver != null) {
                disposableObserver.dispose();
            }
            networkObserver.remove(tag);
        }
    }
}
