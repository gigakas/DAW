package com.dji.daw.controles

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.core.app.ActivityCompat

class PermisosApp (val activity: Activity) {

    fun validarPermisos(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(activity, arrayOf(
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

    }
}