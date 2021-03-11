package com.dji.daw

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.preference.PreferenceManager
import com.dji.daw.controles.AppCompatActivityFullScreen
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import dji.log.DJILog
import dji.sdk.sdkmanager.DJISDKManager

class Configuracion : AppCompatActivityFullScreen(),View.OnClickListener {

    private var bridgeModeEditText: EditText? = null
    private val LAST_USED_BRIDGE_IP = "bridgeip"
    protected var btnRegresarConfig: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracion)
        iniciarUI()
        bridgeModeEditText = findViewById<View>(R.id.editTextIP) as EditText
        bridgeModeEditText!!.setText(PreferenceManager.getDefaultSharedPreferences(this).getString(Configuracion.LAST_USED_BRIDGE_IP, ""))
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

    private fun iniciarUI() {
        btnRegresarConfig = findViewById<Button>(R.id.buttonRegresarConfig) as Button
        btnRegresarConfig!!.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.buttonRegresarConfig -> {
                Toast.makeText(this@Configuracion, "Pulse regresar.", Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    val i = Intent(this@Configuracion, Home::class.java)
                    startActivity(i) //iniciar nueva actividad
                    finish()
                }, 3000)
            }

            else -> {
                Toast.makeText(this@Configuracion, "Error.", Toast.LENGTH_SHORT).show()
            }
        }
    }



    private fun controlRemotoIP() {
        //El usuario ha escrito una IP y se almacena
        val bridgeIP = bridgeModeEditText!!.text.toString()
        if (!TextUtils.isEmpty(bridgeIP)) {
            DJISDKManager.getInstance().enableBridgeModeWithBridgeAppIP(bridgeIP)
            Toast.makeText(applicationContext, "Modo Puente  Encendido\n IP: $bridgeIP", Toast.LENGTH_SHORT).show()
            PreferenceManager.getDefaultSharedPreferences(this).edit().putString(Configuracion.LAST_USED_BRIDGE_IP, bridgeIP).apply()
        }
    }


    companion object {
        private const val TAG = "MainActivity"
        const val LAST_USED_BRIDGE_IP = "bridgeip"
        var isStarted = false
        private const val REQUEST_PERMISSION_CODE = 12345

    }




}