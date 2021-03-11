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

class Profile : AppCompatActivityFullScreen(),View.OnClickListener {

    protected var btnRegresarProfile: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        iniciarUI()

    }

    private fun iniciarUI() {
        btnRegresarProfile = findViewById<Button>(R.id.buttonRegresarProfile) as Button
        btnRegresarProfile!!.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.buttonRegresarProfile -> {
                Toast.makeText(this@Profile, "Pulse regresar.", Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    val i = Intent(this@Profile, Home::class.java)
                    startActivity(i) //iniciar nueva actividad
                    finish()
                }, 3000)
            }

            else -> {
                Toast.makeText(this@Profile, "Error.", Toast.LENGTH_SHORT).show()
            }
        }
    }



}