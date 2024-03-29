package com.dji.daw.controles

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity

abstract class AppCompatActivityFullScreen : AppCompatActivity() {


    override fun onResume() {
        super.onResume()
        setToImmersiveMode()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        val runnable = Runnable { setToImmersiveMode() }
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(runnable, 300)
    }

    private fun setToImmersiveMode() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }


    companion object {
        var usuarioOnlineTokenID:String = ""
        var usuarioOnlineNombresComletos:String = ""
        var usuarioOnlineCorreo:String = ""
        var usuarioOnlineTipoUsuario:String = ""

    }

}