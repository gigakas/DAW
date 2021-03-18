package com.dji.daw

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.Toast
import com.dji.daw.controles.AppCompatActivityFullScreen
import java.net.URLEncoder

class Eventos : AppCompatActivityFullScreen(), View.OnClickListener {

    protected var btnRegresarEventos: Button? = null
    protected var webViewEventos: WebView? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventos)
        iniciarUI()


        val htmlBasseUrl = "https://www.albanss.com/inicioAdministrador"
        val postData =  "tokenUsuario=" + URLEncoder.encode(usuarioOnlineTokenID, "UTF-8") +
                        "&nombreCompletoUsuario=" + URLEncoder.encode(usuarioOnlineNombresComletos, "UTF-8")+
                        "&tipoUsuario=" + URLEncoder.encode(usuarioOnlineTipoUsuario, "UTF-8")

        webViewEventos?.settings?.javaScriptEnabled  = true
        webViewEventos?.postUrl(htmlBasseUrl, postData.toByteArray())

    }

    private fun iniciarUI() {
        btnRegresarEventos = findViewById<View>(R.id.buttonRegresarEventos) as Button
        btnRegresarEventos!!.setOnClickListener(this)
        webViewEventos = findViewById<WebView>(R.id.webViewEventos)
        Toast.makeText(this@Eventos, usuarioOnlineTipoUsuario, Toast.LENGTH_SHORT).show()

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.buttonRegresarEventos -> {
                Toast.makeText(this@Eventos, "Pulse regresar", Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    val i = Intent(this@Eventos, Home::class.java)
                    startActivity(i) //iniciar nueva actividad
                    //finish()
                }, 3000)

            }
            else -> {
                Toast.makeText(this@Eventos, "Error.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}