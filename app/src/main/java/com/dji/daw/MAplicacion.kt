package com.dji.daw

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import com.secneo.sdk.Helper

class MAplicacion : Application() {
    override fun onCreate() {
        super.onCreate()
        val br: BroadcastReceiver = DetectarControlUSB()
        val filter = IntentFilter()
        filter.addAction(ControlUSBConexionActividad.ACCESSORY_ATTACHED)
        registerReceiver(br, filter)
    }

    override fun attachBaseContext(paramContext: Context) {
        super.attachBaseContext(paramContext)
        Helper.install(this@MAplicacion)
    }
}