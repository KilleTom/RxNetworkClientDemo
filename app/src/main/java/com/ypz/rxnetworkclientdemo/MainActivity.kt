package com.ypz.rxnetworkclientdemo

import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.ypz.rxnetworkclient.NetworkType
import com.ypz.rxnetworkclient.RxNetworkClient
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RxNetworkClient.getNetworkClient().getNetStatus(
                connectType =  {
                    when (it) {
                        ConnectivityManager.TYPE_WIFI -> {
                            Log.d("Demo","wifi")
                        }
                        ConnectivityManager.TYPE_MOBILE -> {
                            Log.d("Deni","手机网络")
                        }
                        else->{

                        }
                    }
                },
                unConnect = {

                }
        )

        RxNetworkClient.getNetworkClient().registerNetworkStatus(
                this,
                object : DisposableObserver<NetworkType>() {
                    override fun onComplete() {

                    }


                    override fun onNext(t: NetworkType) {
                        Log.e("ypz", t.name)
                    }

                    override fun onError(e: Throwable) {
                    }

                })


        btn1.setOnClickListener {
            RxNetworkClient.getNetworkClient().checkNetworkCanUse()
        }

        btn2.setOnClickListener {

        }
    }
}
