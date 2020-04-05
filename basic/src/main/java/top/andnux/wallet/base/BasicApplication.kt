package top.andnux.wallet.base

import android.content.Context
import android.content.res.Configuration
import androidx.multidex.MultiDexApplication
import top.andnux.wallet.base.services.ApplicationService
import java.util.*

class BasicApplication : MultiDexApplication() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        for (service in ServiceLoader.load(ApplicationService::class.java)) {
            service.attachBaseContext(base)
        }
    }

    override fun onCreate() {
        super.onCreate()
        for (service in ServiceLoader.load(ApplicationService::class.java)) {
            service.onCreate()
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        for (service in ServiceLoader.load(ApplicationService::class.java)) {
            service.onTerminate()
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        for (service in ServiceLoader.load(ApplicationService::class.java)) {
            service.onLowMemory()
        }
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        for (service in ServiceLoader.load(ApplicationService::class.java)) {
            service.onTrimMemory(level)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        for (service in ServiceLoader.load(ApplicationService::class.java)) {
            service.onConfigurationChanged(newConfig)
        }
    }
}