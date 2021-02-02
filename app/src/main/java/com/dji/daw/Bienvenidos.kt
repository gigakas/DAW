package com.dji.daw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.Toast

class Bienvenidos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienvenidos)

        val btnVamosaVolar = findViewById(R.id.buttonVamosAVolar) as Button


        btnVamosaVolar.setOnClickListener {

            Toast.makeText(this@Bienvenidos, "Pulse a volar.", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                val i = Intent(this@Bienvenidos, MainActivity::class.java)
                startActivity(i) //start new activity
                finish()
            }, 3000)
        }

    }
}