package com.dji.daw

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import dji.common.error.DJIError
import dji.common.error.DJISDKError
import dji.common.useraccount.UserAccountState
import dji.common.util.CommonCallbacks.CompletionCallbackWith
import dji.sdk.base.BaseComponent
import dji.sdk.base.BaseProduct
import dji.sdk.sdkmanager.DJISDKInitEvent
import dji.sdk.sdkmanager.DJISDKManager
import dji.sdk.useraccount.UserAccountManager
import java.util.concurrent.atomic.AtomicBoolean


class Login : AppCompatActivity(), View.OnClickListener {


    protected var loginBtn: Button? = null
    protected var logoutBtn: Button? = null
    protected var bindingStateTV: TextView? = null
    protected var appActivationStateTV: TextView? = null
    private val isRegistrationInProgress = AtomicBoolean(false)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //evaluar permisos al iniciar la aplicacion
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, arrayOf(
                    Manifest.permission.INTERNET, // conexion internet
                    Manifest.permission.WAKE_LOCK, //prender el telefono si esta apagado
                    Manifest.permission.ACCESS_COARSE_LOCATION, //localizacion
                    Manifest.permission.SYSTEM_ALERT_WINDOW, //elertas
                    Manifest.permission.VIBRATE,  // acceso al vibrador phone
                    Manifest.permission.ACCESS_WIFI_STATE,  // WIFI productos conectados
                    Manifest.permission.ACCESS_COARSE_LOCATION,  // Mapas
                    Manifest.permission.ACCESS_NETWORK_STATE,  // WIFI estado de productos conectados via WIFI
                    Manifest.permission.ACCESS_FINE_LOCATION,  // Mapas
                    Manifest.permission.CHANGE_WIFI_STATE,  // Cambio entre conexion USB o WIFI
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,  // Log escritura
                    Manifest.permission.BLUETOOTH,  // Bluetooth productos conectados
                    Manifest.permission.BLUETOOTH_ADMIN,  // Bluetooth administracio productos conectados
                    Manifest.permission.READ_EXTERNAL_STORAGE,  // Log lectura
                    Manifest.permission.READ_PHONE_STATE,  // Identificacion del dispositivo una vez conectado
                    Manifest.permission.RECORD_AUDIO // control altavoces
                    ), 1)
        }

        setContentView(R.layout.activity_login)
        initUI()

/////////////////////////////////////////////////////////
        hideSystemUI()

        /*val btnIngresar = findViewById<Button>(R.id.buttonLogin)
        btnIngresar.setOnClickListener {

            Toast.makeText(this@Login, "Pulse login.", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                val i = Intent(this@Login, Bienvenidos::class.java)
                startActivity(i) //start new activity
                finish()
            }, 3000)
        }

        val btnRegistroUsuario = findViewById<Button>(R.id.buttonRegistroNuevoUsuario)
        btnRegistroUsuario.setOnClickListener {

            Toast.makeText(this@Login, "Pulse registro", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                val i = Intent(this@Login, RegistroUsuario::class.java)
                startActivity(i) //start new activity
                finish()
            }, 3000)
        }*/

    }

    /////////////////////////////////////////////////////////////////////////////

    private val registrationCallback: DJISDKManager.SDKManagerCallback = object : DJISDKManager.SDKManagerCallback {
        override fun onRegister(error: DJIError) {
            isRegistrationInProgress.set(false)
            if (error === DJISDKError.REGISTRATION_SUCCESS) {
                loginAccount()
                DJISDKManager.getInstance().startConnectionToProduct()
                Toast.makeText(applicationContext, "SDK registrado", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext,
                        "SDK no registrado",
                        Toast.LENGTH_LONG).show()
            }
        }

        override fun onProductDisconnect() {
            Toast.makeText(applicationContext,
                    "Aeronave desconectada",
                    Toast.LENGTH_LONG).show()
        }

        override fun onProductConnect(product: BaseProduct) {
            Toast.makeText(applicationContext,
                    "Aeronave conectada",
                    Toast.LENGTH_LONG).show()
        }

        override fun onProductChanged(product: BaseProduct) {}
        override fun onComponentChange(key: BaseProduct.ComponentKey?,
                                       oldComponent: BaseComponent?,
                                       newComponent: BaseComponent?) {
            Toast.makeText(applicationContext,
                    "$key Detectado Cambio",
                    Toast.LENGTH_LONG).show()
        }

        override fun onInitProcess(event: DJISDKInitEvent, totalProcess: Int) {}
        override fun onDatabaseDownloadProgress(current: Long, total: Long) {}
    }


     private fun loginAccount() {
        UserAccountManager.getInstance().logIntoDJIUserAccount(this,
                object : CompletionCallbackWith<UserAccountState?> {
                    override fun onSuccess(userAccountState: UserAccountState?) {
                        Toast.makeText(applicationContext,
                                "Login con DJI OK",
                                Toast.LENGTH_LONG).show()
                    }

                    override fun onFailure(error: DJIError) {
                        Toast.makeText(applicationContext,
                                "Login con DJI error",
                                Toast.LENGTH_LONG).show()
                    }
                })
    }



    public override fun onResume() {
        Log.e(TAG, "onResume")
        super.onResume()
    }

    public override fun onPause() {
        Log.e(TAG, "onPause")
        super.onPause()
    }

    public override fun onStop() {
        Log.e(TAG, "onStop")
        super.onStop()
    }

    fun onReturn() {
        Log.e(TAG, "onReturn")
        finish()
    }

    override fun onDestroy() {
        Log.e(TAG, "onDestroy")
        super.onDestroy()
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }



    private fun initUI() {
        loginBtn = findViewById<View>(R.id.buttonLogin) as Button
        loginBtn!!.setOnClickListener(this)

    }

    override fun onClick(v:View) {
        when (v.id) {
            R.id.buttonLogin -> {
                Toast.makeText(this@Login, "Pulse login.", Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    val i = Intent(this@Login, Bienvenidos::class.java)
                    startActivity(i) //start new activity
                    finish()
                }, 3000)
            }else -> {
            }
        }
    }


    companion object {
        private val TAG = Login::class.java.name
    }


}