# RxNetworkClientDemo
Monitor Android Network Links and State Change and this used the Rxjava2 to Monitor network change.
This client just monitor Android Network Links and State Change，you can use this to build your Tools!  
## How to use?
### Init
Make sure that developers can easily initialize without writing too much code.look like this example
```kotlin
class DemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        /**
         * if you don't need to get networke was connect or unconnect
         * yout can just used this void
         */
        RxNetworkClient.getNetworkClient().initAppliaction(this)
        /**
         * if you need to get networke was connect or unconnect when the RxNetworkClient by init
         * yout can just used this void
         * the connectType just get this networke ConnectivityManager when the network was connect
         * unConnect call to the network was unconnect
         * connectType or unConnect just one will be called back.
         * use this void make sure that init and judge network can be used
         */
        RxNetworkClient.getNetworkClient().initAppliactionNetStatus(
                this,
                connectType = {
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
                    
                })
         
    }
}
```
### used at your logic Module
#### judge network can be used
Some time you need to judge network was working.
In the last example code，just make sure you can know net was working， 
but some times you don't know networke was lose work.
So you can use this example code to konw that was working or not.
```kotlin
        //just judge was working
        RxNetworkClient.getNetworkClient().checkNetworkCanUse()
        //judge was working and when working get ConnctivityManagerType
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
```
#### Monitor network change
Just like this to be use
```kotlin
        RxNetworkClient.getNetworkClient().registerNetworkStatus(
                this,
                object : DisposableObserver<NetworkType>() {
                    override fun onComplete() {

                    }


                    override fun onNext(t: NetworkType) {
                        //do on networke was change，
                    }

                    override fun onError(e: Throwable) {
                        //when was error you can do something
                    }

                })
```
#### remove monitor network change
If you want to remove monitor by use unregisterNetworkStatus void,you will lose this monitor.  
If you need to reset monitor，you must reinvocation registerNetworkStatus and make a new parm DisposableObserver<NetworkType> when after unregisterNetworkStatus void.
remove monitor just like this to be use
```kotlin
    RxNetworkClient.getNetworkClient().unregisterNetworkStatus(this)
````

