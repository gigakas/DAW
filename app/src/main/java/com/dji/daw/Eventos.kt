package com.dji.daw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.webkit.WebView
import android.widget.Button
import android.widget.Toast
import com.dji.daw.controles.AppCompatActivityFullScreen

class Eventos : AppCompatActivityFullScreen() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventos)

        val webView = findViewById<WebView>(R.id.webViewEventos)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("https://www.albanss.com/inicioAdministrador")

        val btnRegresarEventos = findViewById<Button>(R.id.buttonRegresarEventos)
        btnRegresarEventos.setOnClickListener {
            Toast.makeText(this@Eventos, "Pulse regresar ", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                val i = Intent(this@Eventos, Home::class.java)
                startActivity(i) //start new activity
                finish()
            }, 3000)
        }
    }

}