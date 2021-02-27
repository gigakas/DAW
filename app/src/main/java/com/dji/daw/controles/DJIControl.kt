package com.dji.daw.controles

import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import dji.common.error.DJIError
import dji.common.error.DJISDKError
import dji.common.useraccount.UserAccountState
import dji.common.util.CommonCallbacks
import dji.sdk.base.BaseComponent
import dji.sdk.base.BaseProduct
import dji.sdk.sdkmanager.DJISDKInitEvent
import dji.sdk.sdkmanager.DJISDKManager
import dji.sdk.useraccount.UserAccountManager
import java.util.concurrent.atomic.AtomicBoolean

class DJIControl (val applicationContext: Context) {

    private val isRegistrationInProgress = AtomicBoolean(false)
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

            //Evaluo si el drone se ha desconectado
            override fun onProductDisconnect() {
                Toast.makeText(applicationContext,
                        "Aeronave desconectada",
                        Toast.LENGTH_LONG).show()
            }
            //evaluo si el producto esta conectado
            override fun onProductConnect(product: BaseProduct) {
                Toast.makeText(applicationContext,
                        "Aeronave conectada",
                        Toast.LENGTH_LONG).show()
            }

            //Detectar cambions en el hardware del drone y su conexion al mando
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

    //funcion que utiliza el callback de la variable registrationCallback para enviar los mensajes
    //si el usuario ya se ha registrado
    private fun loginAccount() {
        UserAccountManager.getInstance().logIntoDJIUserAccount(applicationContext,
                object : CommonCallbacks.CompletionCallbackWith<UserAccountState?> {
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

    //Funcion que hace la llamada al registro de DJI si  ya esta registrado envia mensaje de ok
    fun startSDKRegistration() {
        if (isRegistrationInProgress.compareAndSet(false, true)) {
            AsyncTask.execute { DJISDKManager.getInstance().registerApp(applicationContext, registrationCallback) }
        }
    }


}