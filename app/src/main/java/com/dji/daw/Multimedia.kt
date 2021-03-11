package com.dji.daw

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Button
import android.widget.Toast
import com.dji.daw.controles.AppCompatActivityFullScreen

class Multimedia : AppCompatActivityFullScreen() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multimedia)

        val btnRegresarMultimedia = findViewById<Button>(R.id.buttonRegresarMultimedia)
        btnRegresarMultimedia.setOnClickListener {
            Toast.makeText(this@Multimedia, "Pulse regresar ", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                val i = Intent(this@Multimedia, Home::class.java)
                startActivity(i) //start new activity
                finish()
            }, 3000)
        }

    }



}