package com.dji.daw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.Toast

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        hideSystemUI()

        val btnIngresar = findViewById<Button>(R.id.buttonLogin)
        val btnRegistroUsuario = findViewById<Button>(R.id.buttonRegistroNuevoUsuario)


        btnIngresar.setOnClickListener {

            Toast.makeText(this@Login, "Pulse login.", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                val i = Intent(this@Login, Bienvenidos::class.java)
                startActivity(i) //start new activity
                finish()
            }, 3000)
        }

        btnRegistroUsuario.setOnClickListener {

            Toast.makeText(this@Login, "Pulse registro", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                val i = Intent(this@Login, RegistroUsuario::class.java)
                startActivity(i) //start new activity
                finish()
            }, 3000)
        }

    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
}