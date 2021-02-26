package com.dji.daw

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dji.sdk.sdkmanager.DJISDKManager

/**

 * Detectar un contro USB conectado y preever si hay un servicio en ejecucion
 */
class DetectarControlUSB : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (!TestComponent.isStarted) {
            val startIntent = context.packageManager
                    .getLaunchIntentForPackage(context.packageName)
            startIntent!!.flags = (Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    or Intent.FLAG_ACTIVITY_NEW_TASK
                    or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
            startIntent.addCategory(Intent.CATEGORY_LAUNCHER)
            context.startActivity(startIntent)
        } else {
            val attachedIntent = Intent()
            attachedIntent.action = DJISDKManager.USB_ACCESSORY_ATTACHED
            context.sendBroadcast(attachedIntent)
        }
    }
}