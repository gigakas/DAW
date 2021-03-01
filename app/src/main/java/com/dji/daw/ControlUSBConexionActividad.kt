package com.dji.daw

import android.app.Activity
import android.content.Intent
import android.hardware.usb.UsbManager
import android.os.Bundle
import android.view.View

/**
 * Detectar la conexion del mando del drone via usb
 *
 */
class ControlUSBConexionActividad : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(View(this))
        val usbIntent = intent
        if (usbIntent != null) {
            val action = usbIntent.action
            if (UsbManager.ACTION_USB_ACCESSORY_ATTACHED == action) {
                val attachedIntent = Intent()
                attachedIntent.action = ACCESSORY_ATTACHED
                sendBroadcast(attachedIntent)
            }
        }
        finish()
    }

    companion object {
        const val ACCESSORY_ATTACHED = "com.dji.ux.sample.ACCESSORY_ATTACHED"
    }
}