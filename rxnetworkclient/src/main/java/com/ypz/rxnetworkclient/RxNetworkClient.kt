package com.ypz.rxnetworkclient

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import io.reactivex.observers.DisposableObserver

/**
 * Created by 易庞宙 on 2019 2019/2/23 00:02
 * email: 1986545332@qq.com
 */
class RxNetworkClient private constructor() {
    private var application: Application? = null
    private var manager: ConnectivityManager? = null
    private val netWorkCallback = object : NetWorkCallback() {
        /**
         * 重写自定义的loast方法避免
         * wifi链接上的时候数据网络断掉了产生了无网络可用的回调，
         * 实际上该网络是可用的
         */
        override fun onLost(network: Network) {
            if (!checkNetworkCanUse()) {
                super.onLost(network)
            }
        }
    }
    private val builder = NetworkRequest.Builder()

    @Throws(NullPointerException::class)
    fun getApplication(): Application {
        if (application == null) throw NullPointerException("get Application is null")
        return application!!
    }

    fun initAppliaction(application: Application) {
        this.application = application
        setNetworkCall()
    }

    fun initAppliactionNetStatus(application: Application, connectType: (Int) -> Unit, unConnect: () -> Unit) {
        initAppliaction(application)
        getNetStatus(connectType, unConnect)
    }

    fun getNetStatus(connectType: (Int) -> Unit, unConnect: () -> Unit) {
        if (manager == null) unConnect
        val infos = manager!!.activeNetworkInfo
        if (infos.isConnected){
            connectType(infos.type)
        }else{
            unConnect()
        }
    }

    @SuppressLint("MissingPermission")
    private fun setNetworkCall() {
        if (manager == null) {
            val request = builder.build()
            manager = application!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (manager == null) return
            manager!!.registerNetworkCallback(request, netWorkCallback)
        } else {
            return
        }
    }

    fun checkNetworkCanUse(): Boolean {
        if (manager == null) return false
        val infos = manager!!.activeNetworkInfo
        return infos?.isConnected ?: false
    }


    fun registerNetworkStatus(tag: Any, observer: DisposableObserver<NetworkType>) {
        RxNetWorkBus.getNetWorkBus().registerNetworkStatus(tag, observer)
    }

    fun unregisterNetworkStatus(tag: Any) {
        RxNetWorkBus.getNetWorkBus().unregisterNetworkStatus(tag)
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var networkClient: RxNetworkClient? = null

        fun getNetworkClient(): RxNetworkClient {
            if (networkClient == null) {
                synchronized(RxNetworkClient::class.java) {
                    networkClient = RxNetworkClient()
                }
            }
            return networkClient!!
        }
    }
}
