package com.dji.daw

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.dji.daw.controles.AppCompatActivityFullScreen
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import dji.common.error.DJIError
import dji.common.error.DJISDKError
import dji.common.useraccount.UserAccountState
import dji.common.util.CommonCallbacks.CompletionCallbackWith
import dji.log.DJILog
import dji.sdk.base.BaseComponent
import dji.sdk.base.BaseProduct
import dji.sdk.base.BaseProduct.ComponentKey
import dji.sdk.sdkmanager.DJISDKInitEvent
import dji.sdk.sdkmanager.DJISDKManager
import dji.sdk.sdkmanager.DJISDKManager.SDKManagerCallback
import dji.sdk.useraccount.UserAccountManager
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

//import android.preference.PreferenceManager;
/**
 * Actividad que muestra opciones de prueba - para usos de programacion
 *
 */
class TestComponent : AppCompatActivityFullScreen(), View.OnClickListener, PopupMenu.OnMenuItemClickListener {
    private val isRegistrationInProgress = AtomicBoolean(false)
    private val registrationCallback: SDKManagerCallback = object : SDKManagerCallback {

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
        override fun onComponentChange(key: ComponentKey?,
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

    private val missingPermission: MutableList<String> = ArrayList()
    private var bridgeModeEditText: EditText? = null


    /// aqui inicia
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testcomponent)
        isStarted = true
        findViewById<View>(R.id.complete_ui_widgets).setOnClickListener(this)
        findViewById<View>(R.id.bt_customized_ui_widgets).setOnClickListener(this)
        findViewById<View>(R.id.bt_map_widget).setOnClickListener(this)
        val versionText = findViewById<View>(R.id.version) as TextView
        versionText.text = resources.getString(R.string.sdk_version, DJISDKManager.getInstance().sdkVersion)
        bridgeModeEditText = findViewById<View>(R.id.edittext_bridge_ip) as EditText
        bridgeModeEditText!!.setText(PreferenceManager.getDefaultSharedPreferences(this).getString(LAST_USED_BRIDGE_IP, ""))
        bridgeModeEditText!!.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || event != null && event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                if (event != null && event.isShiftPressed) {
                    return@setOnEditorActionListener false
                } else {
                    // detectar ip en modo-puente si existe algun valor
                    handleBridgeIPTextChange()
                }
            }
            false // pasar el valor a otro servicio.
        }
        bridgeModeEditText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (s.toString().contains("\n")) {
                    // detectar si el usaurio escribe algun texto
                    val currentText = bridgeModeEditText!!.text.toString()
                    bridgeModeEditText!!.setText(currentText.substring(0, currentText.indexOf('\n')))
                    handleBridgeIPTextChange()
                }
            }
        })
        checkAndRequestPermissions()
    }

    override fun onDestroy() {
        DJISDKManager.getInstance().destroy()
        isStarted = false
        super.onDestroy()
    }

    /**
     * Validar el estatus de los permisos solicitados por la  aplicacion
     */
    private fun checkAndRequestPermissions() {
        // Check for permissions
        for (eachPermission in REQUIRED_PERMISSION_LIST) {
            if (ContextCompat.checkSelfPermission(this, eachPermission) != PackageManager.PERMISSION_GRANTED) {
                missingPermission.add(eachPermission)
            }
        }
        // Request for missing permissions
        if (missingPermission.isEmpty()) {
            startSDKRegistration()
        } else {
            ActivityCompat.requestPermissions(this,
                    missingPermission.toTypedArray(),
                    REQUEST_PERMISSION_CODE)
        }
    }

    /**
     * Resultado de solicitar permisos si son permitidos
     */
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // Check for granted permission and remove from missing list
        if (requestCode == REQUEST_PERMISSION_CODE) {
            for (i in grantResults.indices.reversed()) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    missingPermission.remove(permissions[i])
                }
            }
        }
        // si no hay permisos solicitarlos
        if (missingPermission.isEmpty()) {
            startSDKRegistration()
        } else {
            Toast.makeText(applicationContext, "No se encuentra permisos, no se puede registrar el SDK .", Toast.LENGTH_LONG).show()
        }
    }

    private fun startSDKRegistration() {
        if (isRegistrationInProgress.compareAndSet(false, true)) {
            AsyncTask.execute { DJISDKManager.getInstance().registerApp(this@TestComponent, registrationCallback) }
        }
    }

    override fun onClick(view: View) {
        val nextActivityClass: Class<*>
        val id = view.id
        if (id == R.id.complete_ui_widgets) {
            nextActivityClass = WidgetPrincipalActividad::class.java
        } else if (id == R.id.bt_customized_ui_widgets) {
            nextActivityClass = WidgetsActividadesPersonalizadas::class.java
        } else {
            nextActivityClass = MapWidgetActivity::class.java
            val popup = PopupMenu(this, view)
            popup.setOnMenuItemClickListener(this)
            val popupMenu = popup.menu
            val inflater = popup.menuInflater
            inflater.inflate(R.menu.map_select_menu, popupMenu)
            popupMenu.findItem(R.id.here_map).isEnabled = isHereMapsSupported
            popupMenu.findItem(R.id.google_map).isEnabled = isGoogleMapsSupported(this)
            popup.show()
            return
        }
        val intent = Intent(this, nextActivityClass)
        startActivity(intent)
    }

    @SuppressLint("NonConstantResourceId")
    override fun onMenuItemClick(menuItem: MenuItem): Boolean {
        val intent = Intent(this, MapWidgetActivity::class.java)
        var mapBrand = 0
        when (menuItem.itemId) {
            R.id.here_map -> mapBrand = 0
            R.id.google_map -> mapBrand = 1
            R.id.amap -> mapBrand = 2
            R.id.mapbox -> mapBrand = 3
        }
        intent.putExtra(MapWidgetActivity.MAP_PROVIDER, mapBrand)
        startActivity(intent)
        return false
    }

    private fun handleBridgeIPTextChange() {
        //El usuario ha escrito una IP y se almacena
        val bridgeIP = bridgeModeEditText!!.text.toString()
        if (!TextUtils.isEmpty(bridgeIP)) {
            DJISDKManager.getInstance().enableBridgeModeWithBridgeAppIP(bridgeIP)
            Toast.makeText(applicationContext, "Modo Puente  Encendido\n IP: $bridgeIP", Toast.LENGTH_SHORT).show()
            PreferenceManager.getDefaultSharedPreferences(this).edit().putString(LAST_USED_BRIDGE_IP, bridgeIP).apply()
        }
    }


    companion object {
        private const val TAG = "MainActivity"
        private const val LAST_USED_BRIDGE_IP = "bridgeip"
        var isStarted = false
            private set
        private val REQUIRED_PERMISSION_LIST = arrayOf(
                Manifest.permission.VIBRATE,  // Gimbal rotacion
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
        )
        private const val REQUEST_PERMISSION_CODE = 12345

        //Arquitecturas posible para procesadores strings armeabi, armeabi-v7a, arm64-v8a, x86, x86_64, mips, mips64.
        val isHereMapsSupported: Boolean
            get() {
                val abi: String
                abi = Build.SUPPORTED_ABIS[0]
                DJILog.d(TAG, "abi=$abi")

                //Arquitecturas posible para procesadores strings armeabi, armeabi-v7a, arm64-v8a, x86, x86_64, mips, mips64.
                return abi.contains("arm")
            }

        fun isGoogleMapsSupported(context: Context?): Boolean {
            val googleApiAvailability = GoogleApiAvailability.getInstance()
            val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context)
            return resultCode == ConnectionResult.SUCCESS
        }
    }
}