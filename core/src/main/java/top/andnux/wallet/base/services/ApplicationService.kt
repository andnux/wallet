package top.andnux.wallet.base.services

import android.content.Context
import android.content.res.Configuration

/**
 * 应用服务
 * 主要是初始化
 */
interface ApplicationService {
    fun attachBaseContext(context: Context?)
    fun onCreate()
    fun onTerminate()
    fun onLowMemory()
    fun onTrimMemory(level: Int)
    fun onConfigurationChanged(newConfig: Configuration?)
}