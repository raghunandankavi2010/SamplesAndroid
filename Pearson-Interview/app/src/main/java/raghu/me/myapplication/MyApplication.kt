package raghu.me.myapplication

import android.app.Application
import org.koin.android.ext.android.startKoin
import raghu.me.myapplication.network.applicationModule

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this,listOf(applicationModule))
    }
}