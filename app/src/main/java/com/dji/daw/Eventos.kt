package com.dji.daw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.Toast
import com.dji.daw.controles.AppCompatActivityFullScreen
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Eventos : AppCompatActivityFullScreen(), View.OnClickListener {

    protected var btnRegresarEventos: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventos)

        iniciarUI()

        val webView = findViewById<WebView>(R.id.webViewEventos)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("https://www.albanss.com/inicioAdministrador")

    }

    private fun iniciarUI() {
        btnRegresarEventos = findViewById<View>(R.id.buttonRegresarEventos) as Button
        btnRegresarEventos!!.setOnClickListener(this)


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