package com.dji.daw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.dji.daw.controles.AppCompatActivityFullScreen
import dji.sdk.sdkmanager.DJISDKManager

class ConexionBridgeDrone : AppCompatActivityFullScreen() {

    private var bridgeModeEditText: EditText? = null
    private val LAST_USED_BRIDGE_IP = "bridgeip"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conexion_bridge_drone)

        bridgeModeEditText = findViewById<View>(R.id.editTextIPBridge) as EditText
        bridgeModeEditText!!.setText(PreferenceManager.getDefaultSharedPreferences(this).getString(TestComponent.LAST_USED_BRIDGE_IP, ""))
        bridgeModeEditText!!.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || event != null && event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                if (event != null && event.isShiftPressed) {
                    return@setOnEditorActionListener false
                } else {
                    // detectar ip en modo-puente si existe algun valor
                    controlRemotoIP()
                }
            }
            false // pasar el valor a otro servicio.
        }
    }

    private fun controlRemotoIP() {
        //El usuario ha escrito una IP y se almacena
        val bridgeIP = bridgeModeEditText!!.text.toString()
        if (!TextUtils.isEmpty(bridgeIP)) {
            DJISDKManager.getInstance().enableBridgeModeWithBridgeAppIP(bridgeIP)
            Toast.makeText(applicationContext, "Modo Puente  Encendido\n IP: $bridgeIP", Toast.LENGTH_SHORT).show()
            PreferenceManager.getDefaultSharedPreferences(this).edit().putString(TestComponent.LAST_USED_BRIDGE_IP, bridgeIP).apply()
        }
    }


}