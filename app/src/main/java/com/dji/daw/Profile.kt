package com.dji.daw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.dji.daw.controles.AppCompatActivityFullScreen

class Profile : AppCompatActivityFullScreen() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val btnRegresarProfile = findViewById<Button>(R.id.buttonRegresarProfile)
        btnRegresarProfile.setOnClickListener {
            Toast.makeText(this@Profile, "Pulse regresar ", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                val i = Intent(this@Profile, Home::class.java)
                startActivity(i) //start new activity
                finish()
            }, 3000)
        }

    }
}